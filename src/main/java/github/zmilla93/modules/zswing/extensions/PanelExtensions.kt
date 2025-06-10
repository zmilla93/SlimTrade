package github.zmilla93.modules.zswing.extensions

import java.awt.Dimension
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import javax.swing.JPanel

object PanelExtensions {

    fun JPanel.limitWidth(): JPanel {
        addComponentListener(object : ComponentAdapter() {
            override fun componentResized(e: ComponentEvent) {
                println("LIMITWIDTH")
                preferredSize = null
                preferredSize = Dimension(0, preferredSize.height)
            }
        })
        return this
    }

}