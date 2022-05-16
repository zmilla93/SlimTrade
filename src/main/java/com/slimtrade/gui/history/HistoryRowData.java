package com.slimtrade.gui.history;

import com.slimtrade.core.trading.TradeOffer;

public class HistoryRowData {

    public String date;
    public String time;
    public String player;
    public String item;
    public double price;
    public TradeOffer tradeOffer;

    /**
     * Stores data needed to display a trade in the history window. TradeOffer is not displayed.
     *
     * @param tradeOffer
     */
    public HistoryRowData(TradeOffer tradeOffer) {
        date = tradeOffer.date;
        time = tradeOffer.time;
        player = tradeOffer.playerName;
        item = tradeOffer.itemName;
        price = tradeOffer.priceQuantity;
        this.tradeOffer = tradeOffer;
    }

}
