package github.zmilla93.gui.components

import javax.swing.JLabel

/** A label that wraps text in <html> tags to enable markup & wordwrap. */
class HTMLLabel(text: String) : JLabel("<html>$text</html>")