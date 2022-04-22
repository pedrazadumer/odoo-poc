package odooconsumerpoc;

public class OdooConsumerPocApplication {

    public static void main(String[] args) {
        OdooClient odooClient = new OdooClient();
        OdooInfoPrinter printer = new OdooInfoPrinter();

        printer.printVersionInfo(odooClient.getOdooVersionInfo());
        int uid = odooClient.authenticate();
        printer.printUid(uid);

        printer.printSale(odooClient.listSaleOrderRecords(uid));
    }
}
