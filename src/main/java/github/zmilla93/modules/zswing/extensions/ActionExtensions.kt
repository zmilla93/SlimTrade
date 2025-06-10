package github.zmilla93.modules.zswing.extensions

import java.awt.event.ActionListener
import java.awt.event.MouseEvent
import javax.swing.*
import javax.swing.event.TreeSelectionEvent

object ActionExtensions {

    // TODO : JList onSelectionChange
    // TODO : JTable onSelectionChange
    /**  */

    /** JCheckbox Toggle */
    fun JCheckBox.onToggle(listener: ActionListener): JCheckBox {
        addActionListener(listener)
        return this
    }

    /** JTree Selection */
    fun JTree.onSelectionChange(handler: (TreeSelectionEvent) -> Unit): JTree {
        addTreeSelectionListener {
            handler.invoke(it)
        }
        return this
    }

    /** JButton onClick */
    fun JButton.onClick(runnable: ActionListener): JButton {
        addActionListener(runnable)
        return this
    }

    /** AbstractButton onClick */
    fun AbstractButton.onClick(runnable: ActionListener): AbstractButton {
        addActionListener(runnable)
        return this
    }

    /** Left Mouse Button */
    fun MouseEvent.isLeftButton(): Boolean {
        return SwingUtilities.isLeftMouseButton(this)
    }

    /** Right Mouse Button */
    fun MouseEvent.isRightButton(): Boolean {
        return SwingUtilities.isRightMouseButton(this)
    }

    /** Middle Mouse Button */
    fun MouseEvent.isMiddleButton(): Boolean {
        return SwingUtilities.isMiddleMouseButton(this)
    }

    /** Mouse Double Click */
    fun MouseEvent.isDoubleClick(): Boolean {
        return clickCount == 2
    }

}