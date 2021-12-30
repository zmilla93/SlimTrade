package com.slimtrade.core.chatparser;

import com.slimtrade.core.trading.TradeOffer;

public interface ITradeListener {

    void handleTrade(TradeOffer tradeOffer);

}
