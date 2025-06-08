package github.zmilla93.core.chatparser

import github.zmilla93.core.trading.TradeOffer

interface ITradeListener {
    fun handleTrade(tradeOffer: TradeOffer, loaded: Boolean)
}
