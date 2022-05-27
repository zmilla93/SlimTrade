package com.slimtrade.gui.history;

import com.slimtrade.core.enums.CurrencyImage;

public class PoePrice {

    public final String priceString;
    public final int quantity;
    public final CurrencyImage currencyImage;

    public PoePrice(String priceString, int quantity) {
        this.priceString = priceString;
        this.quantity = quantity;
        this.currencyImage = CurrencyImage.getCurrencyImage(priceString);
    }

}
