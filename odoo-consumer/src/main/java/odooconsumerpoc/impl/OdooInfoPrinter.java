package odooconsumerpoc.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class OdooInfoPrinter {

    public void printVersionInfo(Map<String, Object> versionInfo) {
        System.out.println("============================================");
        System.out.println("             ODOO VERSION INFO              ");
        System.out.println("============================================");
        System.out.printf("Protocol Version: %s\n", versionInfo.get("protocol_version"));
        System.out.printf("Server Serie: %s\n", versionInfo.get("server_serie"));
        System.out.printf("Server Version: %s\n", versionInfo.get("server_version"));
        System.out.print("Server Version Info: ");
        Object[] serverVersionInfo = (Object[]) versionInfo.get("server_version_info");
        System.out.printf("%s\n", Arrays.asList(serverVersionInfo));
        System.out.println("============================================\n\n");
    }

    public void printUid(int uid) {
        System.out.println("============================================");
        System.out.println("           ODOO AUTHENTICATION UID          ");
        System.out.println("============================================");
        System.out.printf("Uid: %s\n", uid);
        System.out.println("============================================\n\n");
    }

    public void printRecordIds(List<Integer> recordIds, String label) {
        System.out.println("============================================");
        System.out.printf("                ODOO %s IDs               \n", label.toUpperCase());
        System.out.println("============================================");
        System.out.printf("%s Ids: %s\n", label, recordIds);
        System.out.println("============================================\n\n");
    }

    public void printSale(Map<String, Object> sale) {
        System.out.println("============================================");
        System.out.printf("        ODOO SALE NUMBER: %s        \n", sale.get("name"));
        System.out.println("============================================");
        System.out.printf("Number: %s\n", sale.get("name"));
        System.out.printf("Creation Date: %s\n", sale.get("create_date"));
        System.out.printf("Customer: %s\n", ((Object[]) sale.get("partner_id"))[1]);
        System.out.printf("Salesperson: %s\n", ((Object[]) sale.get("user_id"))[1]);
        System.out.printf("Next Activity: %s\n", sale.get("activity_summary"));
        System.out.printf("Total: %s\n", sale.get("amount_total"));
        System.out.printf("Currency: %s\n", ((Object[]) sale.get("currency_id"))[1]);
        System.out.println("============================================\n\n");
    }
}
