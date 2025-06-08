package github.zmilla93.gui.components

import javax.swing.JTextPane

/** A label that supports HTML markup. */
class HTMLLabel(text: String) : JTextPane() {

    init {
        setContentType("text/html")
        this.text = text
        border = null
        isEditable = false
        isFocusable = false
    }

}