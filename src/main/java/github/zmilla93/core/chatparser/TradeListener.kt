package github.zmilla93.core.chatparser

import github.zmilla93.core.trading.TradeOffer

interface TradeListener {
    fun handleTrade(tradeOffer: TradeOffer, loaded: Boolean)
}
