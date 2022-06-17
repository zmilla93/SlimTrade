package com.slimtrade.gui.history;

import com.slimtrade.core.enums.CurrencyType;

public class PoePrice {

    public final String priceString;
    public final int quantity;
    public final CurrencyType currencyType;

    public PoePrice(String priceString, int quantity) {
        this.priceString = priceString;
        this.quantity = quantity;
        this.currencyType = CurrencyType.getCurrencyImage(priceString);
    }

}
