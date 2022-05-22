package com.slimtrade.core.data;

import java.util.ArrayList;

public class SaleItem {

    public final String itemName;
    public final int quantity;

    public SaleItem(String itemName, int quantity) {
        this.itemName = itemName;
        this.quantity = quantity;
    }

    public static ArrayList<SaleItem> getItems(String line) {
        StringBuilder builder = new StringBuilder();
        ArrayList<SaleItem> items = new ArrayList<>();
        int quantity = 0;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == ' ' && builder.length() > 0) {
                if (builder.toString().matches("\\d+")) {
                    quantity = Integer.parseInt(builder.toString());
                    builder.setLength(0);
                } else {
                    builder.append(c);
                }
            } else if (c == ',' && line.length() > i + 2) {
                char lookAhead = line.charAt(i + 2);
                if (lookAhead >= '0' && lookAhead <= '9') {
                    items.add(new SaleItem(builder.toString(), quantity));
                    builder.setLength(0);
                }
            } else {
                if (c == ' ' && builder.length() == 0) continue;
                builder.append(c);
            }
        }
        if (builder.length() > 0) {
            items.add(new SaleItem(builder.toString(), quantity));
            builder.setLength(0);
        }
        return items;
    }

}
