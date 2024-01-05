package com.slimtrade.gui.history;

import com.slimtrade.core.data.SaleItem;
import com.slimtrade.core.data.SaleItemWrapper;
import com.slimtrade.core.trading.TradeOffer;

public class HistoryRowData {

    public final DateString date;
    public final TimeString time;
    //    public String player;
    public PlayerNameWrapper playerNameWrapper;
    public SaleItemWrapper saleItemWrapper;
    //    public String item;
    //    public double price;
    public PoePrice price;
    public TradeOffer tradeOffer;

    /**
     * Stores data needed to display a trade in the history window. TradeOffer is not displayed.
     */

    public HistoryRowData(TradeOffer tradeOffer) {
        date = new DateString(tradeOffer.date);
        time = new TimeString(tradeOffer.time);
//        player = tradeOffer.playerName;
        playerNameWrapper = new PlayerNameWrapper(tradeOffer.playerName);
        saleItemWrapper = new SaleItemWrapper();
        saleItemWrapper.items = SaleItem.getItems(tradeOffer.itemQuantityString + " " + tradeOffer.itemName);
        price = new PoePrice(tradeOffer.priceName, (int) tradeOffer.priceQuantity);
        this.tradeOffer = tradeOffer;
    }

}
