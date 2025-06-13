package io.github.zmilla93.gui.components.cardpanel

import java.awt.CardLayout
import java.awt.Component
import javax.swing.JPanel

/**
 * A panel that simplifies working with [CardLayout]s.
 * Add cards using [add], then use [showCard] to select which card is visible.
 * Does NOT currently support removing components (will cause bugs).
 */
// FIXME : Add support for removing components? Could use a queue of int, track max id to fill queue, then requeue an int when it is removed.
open class CardPanel() : JPanel() {
    private val cardLayout = CardLayout()
    private val cardMap = HashMap<Component, Int>()
    var currentCardIndex: Int = 0
        private set
    private var allowWrap = false
    var currentCard: Component? = null
        private set

    init {
        layout = cardLayout
    }

    constructor(vararg components: Component) : this() {
        for (component in components) add(component)
        if (components.size > 0) currentCard = components[0]
    }

    /**
     * Use this to show a component that was previously added.
     *
     * @param component Component to show
     */
    fun showCard(component: Component) {
        if (!cardMap.containsKey(component)) return
        val key = cardMap[component]!!
        currentCardIndex = key
        cardLayout.show(this, key.toString())
        parent.revalidate()
        parent.repaint()
        currentCard = component
    }

    /**
     * Shows a card by index. Will throw an error if the index is invalid.
     */
    fun showCard(index: Int) {
        cardLayout.show(this, index.toString())
    }

    /**
     * Sets if previous() and next() will cause the card panel to wrap between the first and last panels.
     */
    fun setAllowWrapAround(allow: Boolean) {
        this.allowWrap = allow
    }

    fun previous() {
        val wrap = currentCardIndex == 0
        if (wrap && !allowWrap) return
        cardLayout.previous(this)
        if (wrap) currentCardIndex = componentCount - 1
        else currentCardIndex--
    }

    fun next() {
        val wrap = currentCardIndex == componentCount - 1
        if (wrap && !allowWrap) return
        cardLayout.next(this)
        if (wrap) currentCardIndex = 0
        else currentCardIndex++
    }

    /**
     * An [add] alias to make it easier to identify the correct add method.
     */
    fun addCard(comp: Component): Component {
        return add(comp)
    }

    /**
     * This is the only version of add() that should ever be used!
     */
    override fun add(comp: Component): Component {
        if (componentCount == 0) currentCard = comp
        val key = componentCount
        add(comp, key.toString())
        cardMap[comp] = key
        return comp
    }
}
