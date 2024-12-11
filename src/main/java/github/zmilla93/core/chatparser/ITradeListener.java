package github.zmilla93.core.chatparser;

import github.zmilla93.core.trading.TradeOffer;

public interface ITradeListener {

    void handleTrade(TradeOffer tradeOffer, boolean loaded);

}
