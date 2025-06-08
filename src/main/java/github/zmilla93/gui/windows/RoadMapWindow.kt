package github.zmilla93.gui.windows

import github.zmilla93.gui.options.AbstractOptionPanel

class RoadMapWindow : CustomDialog("RoadMap") {

    val panel = AbstractOptionPanel()

    init {
        add(panel)
        panel
    }

}