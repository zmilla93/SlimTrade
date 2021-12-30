package com.slimtrade.core.chatparser;

import com.slimtrade.core.trading.TradeOffer;

public interface IPreloadTradeListener {

    void handlePreloadTrade(TradeOffer tradeOffer);

}
