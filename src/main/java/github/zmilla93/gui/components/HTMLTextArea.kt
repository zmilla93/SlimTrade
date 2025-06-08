package github.zmilla93.gui.components

import javax.swing.JTextPane

/** A text area that has word wrap enabled. */
class HTMLTextArea(text: String? = null) : JTextPane() {

    init {
        isEditable = false
        isFocusable = false
        contentType = "text/html"
        this.text = text
    }

}