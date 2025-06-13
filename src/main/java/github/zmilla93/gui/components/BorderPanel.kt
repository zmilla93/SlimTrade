package github.zmilla93.gui.components

import java.awt.BorderLayout
import javax.swing.JComponent
import javax.swing.JPanel

class BorderPanel(child: JComponent, constraint: String = BorderLayout.NORTH) : JPanel() {

    init {
        layout = BorderLayout()
        add(child, constraint)
    }

}