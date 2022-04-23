package odooconsumerpoc.impl;

import odooconsumerpoc.OdooRpcClient;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OdooXmlRpcRpcClient implements OdooRpcClient {

    private static final String ODOO_SERVER_URL = "http://localhost:8069";
    private static final String ODOO_DATABASE = "odoo";
    private static final String ODOO_DATABASE_USERNAME = "demo";
    private static final String ODOO_DATABASE_PWD = "demo";
    private static final String RPC_AUTHENTICATE_METHOD_NAME = "authenticate";
    private static final String RPC_SEARCH_METHOD_NAME = "execute_kw";
    private static final String COMMON_ENDPOINT = String.format("%s/xmlrpc/2/common", ODOO_SERVER_URL);
    private static final String OBJECT_ENDPOINT = String.format("%s/xmlrpc/2/object", ODOO_SERVER_URL);

    private final XmlRpcClient xmlRpcClient;
    private final XmlRpcClientConfigImpl commonEndpointConfig;
    private final XmlRpcClientConfigImpl objectEndpointConfig;

    public OdooXmlRpcRpcClient() {
        this.xmlRpcClient = new XmlRpcClient();
        this.commonEndpointConfig = new XmlRpcClientConfigImpl();
        this.objectEndpointConfig = new XmlRpcClientConfigImpl();

        try {
            this.commonEndpointConfig.setServerURL(new URL(COMMON_ENDPOINT));
            this.objectEndpointConfig.setServerURL(new URL(OBJECT_ENDPOINT));
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error building the Odoo Client. " +
                    "Is the Odoo server URL correct and the server is up and running?", e);
        }
    }

    @Override
    public Map<String, Object> getServerVersionInfo() {
        try {
            return (Map<String, Object>) xmlRpcClient.execute(this.commonEndpointConfig, "version",
                    Collections.emptyList());
        } catch (XmlRpcException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to retrieve the version information from Odoo server.", e);
        }
    }

    @Override
    public int authenticate() {
        try {
            Object rawAuthResponse = xmlRpcClient.execute(this.commonEndpointConfig,
                    RPC_AUTHENTICATE_METHOD_NAME,
                    Arrays.asList(ODOO_DATABASE, ODOO_DATABASE_USERNAME, ODOO_DATABASE_PWD, Collections.emptyList()));
            if (rawAuthResponse instanceof Integer) {
                return (int) rawAuthResponse;
            }
            throw new RuntimeException("Odoo authentication failed. Did you use the right credentials?");
        } catch (XmlRpcException e) {
            throw new RuntimeException("Odoo authentication failed.", e);
        }
    }

    @Override
    public List<Integer> findRecordIdsByModelAndFilter(int uid, String model, List<Object> filterCriteria) {
        try {
            return Arrays.stream((Object[]) this.xmlRpcClient.execute(this.objectEndpointConfig,
                    RPC_SEARCH_METHOD_NAME, Arrays.asList(
                            ODOO_DATABASE, uid, ODOO_DATABASE_PWD,
                            model, "search",
                            Arrays.asList(Arrays.asList(filterCriteria)),
                            new HashMap() {{
                                put("limit", 100);
                            }}))).map(Integer.class::cast).collect(Collectors.toList());
        } catch (XmlRpcException e) {
            throw new RuntimeException("Search record Ids for Odoo model failed.", e);
        }
    }

    @Override
    public Map<String, Object> findRecordByIdAndModel(int uid, String model, int recordId) {
        try {
            return (Map<String, Object>) ((Object[]) this.xmlRpcClient.execute(this.objectEndpointConfig,
                    RPC_SEARCH_METHOD_NAME, Arrays.asList(
                            ODOO_DATABASE, uid, ODOO_DATABASE_PWD,
                            model, "read",
                            Arrays.asList(recordId)
                    )
            ))[0];
        } catch (XmlRpcException e) {
            throw new RuntimeException("Find record by Ids and Model failed.", e);
        }
    }
}
