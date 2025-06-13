package io.github.zmilla93.gui.components.cardpanel

/**
 * Interface for a [CardPanelChild] that wants to inherit from a different base class.
 * Implementation should always be "CardPanelChild.showCard(this)".
 */
interface ICardPanelChild {

    fun showCard()

}