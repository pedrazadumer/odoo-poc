package odooconsumerpoc;

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

public class OdooClient {

    private final XmlRpcClient client;
    private final XmlRpcClientConfigImpl rootEndpointConfig;
    private final XmlRpcClientConfigImpl commonEndpointConfig;
    private final String url = "http://localhost:8069";
    private final String db = "odoo";
    private final String username = "demo";
    private final String password = "demo";

    public OdooClient() {
        this.client = new XmlRpcClient();
        this.rootEndpointConfig = new XmlRpcClientConfigImpl();
        this.commonEndpointConfig = new XmlRpcClientConfigImpl();

        try {
            this.rootEndpointConfig.setServerURL(new URL(String.format("%s", url)));
            this.commonEndpointConfig.setServerURL(new URL(String.format("%s/xmlrpc/2/common", url)));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public int authenticate() {
        try {
            return (int) client.execute(this.commonEndpointConfig, "authenticate", Arrays.asList(db, username, password, Collections.emptyList()));
        } catch (XmlRpcException e) {
            e.printStackTrace();
            throw new RuntimeException("Odoo authentication failed.", e);
        }
    }

    public Map<String, Object> getDatabaseInfo() {

        Map<String, Object> info = null;
        try {
            info = (Map<String, Object>) this.client.execute(
                    this.rootEndpointConfig, "start", Collections.emptyList());
        } catch (XmlRpcException e) {
            e.printStackTrace();
        }
        return info;
    }

    public Map<String, Object> getOdooVersionInfo() {
        try {
            return (Map<String, Object>) client.execute(this.commonEndpointConfig, "version",
                    Collections.emptyList());
        } catch (XmlRpcException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to retrieve the version information from Odoo server.", e);
        }
    }

    public List<Object> listRecords(int uid) {

        final XmlRpcClient models = new XmlRpcClient() {{
            setConfig(new XmlRpcClientConfigImpl() {{
                try {
                    setServerURL(new URL(String.format("%s/xmlrpc/2/object", url)));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }});
        }};

        try {
            return Arrays.asList((Object[]) models.execute("execute_kw", Arrays.asList(
                    db, uid, password,
                    "res.partner", "search",
                    Arrays.asList(Arrays.asList(
                            Arrays.asList("is_company", "=", true)))
            )));
        } catch (XmlRpcException e) {
            e.printStackTrace();
            throw new RuntimeException("List Odoo records failed.", e);
        }
    }

    public Map listSaleOrderRecords(int uid) {

        final XmlRpcClient models = new XmlRpcClient() {{
            setConfig(new XmlRpcClientConfigImpl() {{
                try {
                    setServerURL(new URL(String.format("%s/xmlrpc/2/object", url)));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }});
        }};

        try {
//            List<Object> ids = Arrays.asList((Object[]) models.execute("execute_kw", Arrays.asList(
//                    db, uid, password,
//                    "sale.order", "search",
//                    Arrays.asList(Arrays.asList(
//                            Arrays.asList("user_id", "=", 6)))
//            )));

            final List ids = Arrays.asList((Object[])models.execute(
                    "execute_kw", Arrays.asList(
                            db, uid, password,
                            "sale.order", "search",
                            Arrays.asList(Arrays.asList(
                                    Arrays.asList("user_id", "=", 6))),
                            new HashMap() {{ put("limit", 1); }})));

            return (Map)((Object[])models.execute(
                    "execute_kw", Arrays.asList(
                            db, uid, password,
                            "sale.order", "read",
                            ids
                    )
            ))[0];
        } catch (XmlRpcException e) {
            e.printStackTrace();
            throw new RuntimeException("List Odoo records failed.", e);
        }
    }
}
