package github.zmilla93.gui.components

import java.awt.GridBagLayout
import javax.swing.JComponent
import javax.swing.JPanel

class CenterPanel(child: JComponent) : JPanel() {

    init {
        layout = GridBagLayout()
        add(child)
    }

}