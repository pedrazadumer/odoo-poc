package odooconsumerpoc;

import odooconsumerpoc.impl.OdooInfoPrinter;
import odooconsumerpoc.impl.OdooXmlRpcRpcClient;

import java.util.Arrays;
import java.util.List;

public class OdooConsumerPocApplication {

    public static final String SALE_ORDER_MODEL = "sale.order";

    public static void main(String[] args) {
        OdooXmlRpcRpcClient odooXmlRpcClient = new OdooXmlRpcRpcClient();
        OdooInfoPrinter printer = new OdooInfoPrinter();
        int uid;

        printer.printVersionInfo(odooXmlRpcClient.getServerVersionInfo());

        uid = odooXmlRpcClient.authenticate();
        printer.printUid(uid);

        List<Integer> saleIds = odooXmlRpcClient.findRecordIdsByModelAndFilter(uid, SALE_ORDER_MODEL, Arrays.asList("user_id", "=", uid));
        printer.printRecordIds(saleIds, "Sale");

        saleIds.forEach(saleId -> printer.printSale(odooXmlRpcClient.findRecordByIdAndModel(uid, SALE_ORDER_MODEL, saleId)));


        //printer.printSale(odooXmlRpcClient.listSaleOrderRecords(uid));
    }
}
