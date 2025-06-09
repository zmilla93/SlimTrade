package github.zmilla93.gui.components

import github.zmilla93.core.utility.GUIReferences
import github.zmilla93.core.utility.ZUtil
import github.zmilla93.modules.zswing.extensions.StyleExtensions.bold
import io.github.zmilla93.modules.theme.UIProperty.FontSizeExtensions.fontSize
import java.awt.*
import javax.swing.Box
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JSeparator
import javax.swing.border.EmptyBorder
import javax.swing.border.LineBorder

open class BoxPanel(inset: Int = 10) : JPanel() {

    val gc: GridBagConstraints = ZUtil.getGC()

    init {
        border = LineBorder(Color.RED)
        layout = GridBagLayout()
        gc.weightx = 1.0
        gc.weighty = 0.0
        gc.fill = GridBagConstraints.HORIZONTAL
        border = EmptyBorder(inset, inset, inset, inset)
    }

    override fun add(comp: Component): Component {
        super.add(comp, gc)
        gc.gridy++
        return comp
    }

    fun addWithInset(comp: Component, inset: Insets): Component {
        val prevInset = gc.insets
        gc.insets = inset
        add(comp)
        gc.insets = prevInset
        return comp
    }

    fun addCenter(comp: Component): Component {
        val weightx = gc.weightx
        val fill = gc.fill
        val anchor = gc.anchor
        gc.weightx = 0.0
        gc.anchor = GridBagConstraints.CENTER
        gc.fill = GridBagConstraints.NORTH
        add(comp)
        gc.weightx = weightx
        gc.fill = fill
        gc.anchor = anchor
//        gc.gridy++
        return comp
    }

    fun label(text: String): JLabel {
        return add(JLabel(text)) as JLabel
    }

    fun htmlLabel(text: String): HTMLLabel {
        return add(HTMLLabel(text)) as HTMLLabel
    }

    fun header(text: String, headerSize: Int = 0) {
        val header = JLabel(text).fontSize(14).bold()
        if (headerSize > 0) header.fontSize(headerSize)
        add(header)
        separator()
        verticalStrutSmall()
    }

    fun separator() {
        val fill = gc.fill
        val weightY = gc.weighty
        gc.fill = GridBagConstraints.BOTH
        gc.weighty = 1.0
        add(JSeparator(JSeparator.HORIZONTAL))
        gc.fill = fill
        gc.weighty = weightY
    }

    fun verticalStrutSmall() {
        verticalStrut(GUIReferences.SMALL_INSET)
    }


    fun verticalStrut(size: Int = GUIReferences.INSET) {
        add(Box.createVerticalStrut(size))
    }

}