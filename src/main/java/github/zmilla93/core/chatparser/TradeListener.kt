package github.zmilla93.core.chatparser

import github.zmilla93.core.trading.TradeOffer

@Deprecated("Switch to TradeEvent on bus")
interface TradeListener {
    fun handleTrade(tradeOffer: TradeOffer, loaded: Boolean)
}
