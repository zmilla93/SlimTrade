package io.github.zmilla93.gui.components.cardpanel

import io.github.zmilla93.gui.components.cardpanel.CardPanelChild.Companion.showCard
import javax.swing.JPanel

/**
 * A child of a [CardPanel] or [CardPanelVirtual]. Can use [showCard] to become the visible card.
 */
open class CardPanelChild : JPanel() {

    fun showCard() {
        showCard(this)
    }

    companion object {

        fun showCard(panel: JPanel) {
            val parent = panel.parent
            if (parent == null) return
            if (parent is CardPanel) parent.showCard(panel)
            else if (parent is CardPanelVirtual) parent.showCard(panel)
        }

    }

}