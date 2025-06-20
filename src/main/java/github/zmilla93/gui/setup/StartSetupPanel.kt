package github.zmilla93.gui.setup

import github.zmilla93.core.utility.ZUtil.getGC
import java.awt.GridBagLayout
import javax.swing.JLabel
import javax.swing.JPanel

class StartSetupPanel : JPanel() {

    init {
        setLayout(GridBagLayout())
        val gc = getGC()
        add(JLabel("Welcome to SlimTrade!"), gc)
        gc.gridy++
        add(JLabel("Let's do some quick setup."), gc)
        gc.gridy++
    }

}
