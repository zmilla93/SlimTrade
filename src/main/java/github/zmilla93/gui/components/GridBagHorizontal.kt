package github.zmilla93.gui.components

import github.zmilla93.core.utility.ZUtil
import java.awt.Component
import javax.swing.JPanel

class GridBagHorizontal(
    vararg initialComponents: Component,
    fill: Int? = null,
    weightX: Double? = null
) : JPanel() {

//    constructor(vararg comp: Component) : this()

    val gc = ZUtil.getGC()

    init {
        if (fill != null) gc.fill = fill
        if (weightX != null) gc.weightx = weightX
    }

    override fun add(comp: Component): Component? {
        super.add(comp, gc)
        gc.gridx++
        return comp
    }

}