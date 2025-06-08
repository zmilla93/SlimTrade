package github.zmilla93.gui.components

import javax.swing.JTextArea

/** A text area that has word wrap enabled. */
class WordWrapTextArea(text: String? = null) : JTextArea(text) {

    init {
        lineWrap = true
        wrapStyleWord = true
        isEditable = false
        isFocusable = false
    }

}