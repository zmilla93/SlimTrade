package github.zmilla93.modules.zswing.extensions

import java.awt.Dimension
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import javax.swing.JComponent
import javax.swing.JPanel

/**
 * Limits a panel's width by ensuring it always has a preferred width of 0
 * and a preferred height that is dynamic (assigned by layout manager).
 *
 * This is required for components with dynamic sizing that are also inside
 * a ScrollPane, such as HTML with word wrapping.
 */
object PanelExtensions {

    fun JComponent.fitParentWidth(): JComponent {
        fitPanelToParentWidth(this)
        addComponentListener(object : ComponentAdapter() {
            override fun componentResized(e: ComponentEvent) {
                fitPanelToParentWidth(this@fitParentWidth)
            }
        })
        addPropertyChangeListener("ancestor") {
            fitPanelToParentWidth(this@fitParentWidth)
        }
        return this
    }

    fun JPanel.fitParentWidth(): JPanel {
        addComponentListener(object : ComponentAdapter() {
            override fun componentResized(e: ComponentEvent) {
                preferredSize = null
                preferredSize = Dimension(0, preferredSize.height)
            }
        })
        return this
    }

    fun fitPanelToParentWidth(comp: JComponent): JComponent {
        comp.preferredSize = null
        comp.preferredSize = Dimension(0, comp.preferredSize.height)
        return comp
    }

}