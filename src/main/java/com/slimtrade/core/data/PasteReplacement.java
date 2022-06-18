package com.slimtrade.core.data;

public class PasteReplacement {

    public final String self;
    public final String playerName;
    public final String itemName; // Item Quanitity + Item Name
    public final int itemQuantity; // Item Quanitity + Item Name
    public final String priceName; // Price Quantity + Price Name
    public final double priceQuantity; // Price Quantity + Price Name

    public PasteReplacement(String self, String player){
        this.self = self;
        this.playerName = player;
        this.itemName = "";
        this.itemQuantity = 0;
        this.priceName = "";
        this.priceQuantity = 0;
    }

    public PasteReplacement(String self, String player, String itemName, int itemQuantity, String priceName, double priceQuantity){
        this.self = self;
        this.playerName = player;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.priceName = priceName;
        this.priceQuantity = priceQuantity;
    }

}
