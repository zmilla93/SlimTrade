package com.slimtrade.core.data;

import com.slimtrade.core.enums.CurrencyType;

import java.util.ArrayList;

public class SaleItem {

    public final String itemName;
    public final double quantity;
    public final CurrencyType currencyType;

    /**
     * Represents an item OR currency type, along with a quantity.
     *
     * @param itemName Item name or currency name
     * @param quantity Quantity
     */
    public SaleItem(String itemName, double quantity) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.currencyType = CurrencyType.getCurrencyType(itemName);
    }

    public ArrayList<SaleItem> toArrayList() {
        ArrayList<SaleItem> list = new ArrayList<>(1);
        list.add(this);
        return list;
    }

    /**
     * Parses a string and returns a list of SaleItems.
     *
     * @param text String to parse
     * @return List of SaleItems
     */
    public static ArrayList<SaleItem> getItems(String text) {
        StringBuilder builder = new StringBuilder();
        ArrayList<SaleItem> items = new ArrayList<>();
        int quantity = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == ' ' && builder.length() > 0) {
                if (builder.toString().matches("\\d+")) {
                    quantity = Integer.parseInt(builder.toString());
                    builder.setLength(0);
                } else {
                    builder.append(c);
                }
            } else if (c == ',' && text.length() > i + 2) {
                char lookAhead = text.charAt(i + 2);
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
