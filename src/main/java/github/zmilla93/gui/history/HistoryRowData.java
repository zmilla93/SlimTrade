package github.zmilla93.gui.history;

import github.zmilla93.core.data.SaleItem;
import github.zmilla93.core.data.SaleItemWrapper;
import github.zmilla93.core.trading.TradeOffer;

/**
 * Stores formatted trade offer data needed for display in the history window.
 */
public class HistoryRowData {

    // IMPORTANT: Variable order must match display order!
    public final DateString date;
    public final TimeString time;
    public final PlayerNameWrapper playerNameWrapper;
    public final SaleItemWrapper saleItemWrapper;
    public final SaleItemWrapper price;

    // Not displayed
    public final TradeOffer tradeOffer;

    public HistoryRowData(TradeOffer tradeOffer) {
        this.tradeOffer = tradeOffer;
        date = new DateString(tradeOffer.date);
        time = new TimeString(tradeOffer.time);
        playerNameWrapper = new PlayerNameWrapper(tradeOffer.playerName);
        saleItemWrapper = new SaleItemWrapper(SaleItem.getItems(tradeOffer.itemQuantityString + " " + tradeOffer.itemName));
        price = new SaleItemWrapper(new SaleItem(tradeOffer.priceName, tradeOffer.priceQuantity).toArrayList());
    }

}
