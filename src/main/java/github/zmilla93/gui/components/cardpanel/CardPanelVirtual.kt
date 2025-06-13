package io.github.zmilla93.gui.components.cardpanel

import java.awt.BorderLayout
import java.awt.Component
import java.awt.GridBagLayout
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.SwingUtilities

/**
 * Similar to a [CardPanel], but removes inactive panels from the hierarchy.
 * This prevents the size of hidden components from affecting the container size.
 */
class CardPanelVirtual : JPanel() {

    /** IMPORTANT: The component list must be nullable since they can be queried before the object is fully initialized. */
    private val components: ArrayList<Component>? = ArrayList()

    override fun add(comp: Component): Component {
        components!!.add(comp)
        if (components.size == 1) showCard(comp)
        return comp
    }

    fun addCard(comp: Component): Component {
        return add(comp)
    }

    // FIXME : The fullscreen param is currently unused. If it does get used,
    //  it should be moved to the add function to ensure it is only ever defined once.
    fun showCard(component: Component, fullScreen: Boolean = true) {
        if (!components!!.contains(component)) throw RuntimeException("Panel must be added to CardPanelVirtual before it can be shown!")
        removeAll()
        if (fullScreen) {
            setLayout(BorderLayout())
            super.add(component, BorderLayout.CENTER)
        } else {
            setLayout(GridBagLayout())
            super.add(component)
        }
        if (parent != null) parent.revalidate()
    }

    /** Inactive cards are removed from the component hierarchy, so their component trees must be updated manually. */
    override fun updateUI() {
        super.updateUI()
        if (components == null) return
        for (comp in components) if (comp is JComponent) {
            if (comp.getParent() != null) continue
            SwingUtilities.updateComponentTreeUI(comp)
        }
    }

    /** Inactive cards are removed from the component hierarchy, so return the backing array instead. */
    override fun getComponents(): Array<Component> {
        return components!!.toTypedArray()
    }

}
