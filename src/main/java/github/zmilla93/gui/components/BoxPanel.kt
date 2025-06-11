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
    var strutSize = 16
    var headerSize = 0
    var headerInset = 0
    var leftInset: Int = 0
//        get() = gc.insets.left
//        set(value) {
//            gc.insets.left = value
//            field = value
//        }

    init {
        border = LineBorder(Color.RED)
        layout = GridBagLayout()
        gc.weightx = 1.0
        gc.weighty = 0.0
        gc.fill = GridBagConstraints.HORIZONTAL
        gc.insets.left = leftInset
//        gc.anchor = GridBagConstraints.WEST
        border = EmptyBorder(inset, inset, inset, inset)
    }

    override fun add(comp: Component): Component {
        gc.insets.left = leftInset
        super.add(comp, gc)
        gc.gridy++
        return comp
    }

    fun addNoInset(comp: Component): Component {
        gc.insets.left = 0
        super.add(comp, gc)
        gc.insets.left = leftInset
        gc.gridy++
        return comp
    }

    fun addWithInset(comp: Component, inset: Int): Component {
        val prevInset = gc.insets.left
        gc.insets.left = inset + leftInset
        add(comp, gc)
        gc.gridy++
        gc.insets.left = prevInset
        return comp
    }

    fun addWithInset(comp: Component, inset: Insets): Component {
        val prevInset = gc.insets
        gc.insets = inset
        add(comp, gc)
        gc.gridy++
        gc.insets = prevInset
        return comp
    }

    fun addWithLeftInset(comp: Component, inset: Int): Component {
        val prevInset = gc.insets
        gc.insets = Insets(0, inset, 0, 0)
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
        gc.fill = GridBagConstraints.NONE
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

    fun header(text: String, headerSize: Int = this.headerSize) {
        val header = JLabel(text).bold()
        if (headerSize > 0) header.fontSize(headerSize)
        addNoInset(header)
        separator()
        verticalStrutSmall()
    }

    fun separator() {
        val fill = gc.fill
        val weightY = gc.weighty
        gc.fill = GridBagConstraints.BOTH
        gc.weighty = 1.0
        addNoInset(JSeparator(JSeparator.HORIZONTAL))
        gc.fill = fill
        gc.weighty = weightY
    }

    fun strut(size: Int = strutSize) {
        add(Box.createVerticalStrut(size))
    }

    fun strutSmall() {
        add(Box.createVerticalStrut(GUIReferences.SMALL_INSET))
    }

    fun verticalStrutSmall() {
        verticalStrut(GUIReferences.SMALL_INSET)
    }


    fun verticalStrut(size: Int = GUIReferences.INSET) {
        add(Box.createVerticalStrut(size))
    }

}