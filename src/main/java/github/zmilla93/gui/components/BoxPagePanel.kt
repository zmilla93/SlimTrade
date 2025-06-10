package github.zmilla93.gui.components

import javax.swing.BoxLayout
import javax.swing.JPanel

class BoxPagePanel : JPanel() {

    init {
        layout = BoxLayout(this, BoxLayout.PAGE_AXIS)
    }

}