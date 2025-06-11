package github.zmilla93.gui.components

import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.JComponent
import javax.swing.JPanel

/**
 * Wraps a single component with an inset.
 */
class BufferPanel(component: JComponent, top: Int, left: Int, bottom: Int, right: Int) : JPanel() {

    constructor(component: JComponent, inset: Int) : this(component, inset, inset, inset, inset)

    init {
        setLayout(GridBagLayout())
        val gc = GridBagConstraints()
        gc.fill = GridBagConstraints.BOTH
        gc.weightx = 1.0
        gc.weighty = 1.0
        gc.insets = Insets(top, left, bottom, right)
        add(component, gc)

    }
}
