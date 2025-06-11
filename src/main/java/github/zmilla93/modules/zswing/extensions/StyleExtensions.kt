package github.zmilla93.modules.zswing.extensions

import java.awt.Font
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.border.Border

object StyleExtensions {

    /** Clears a [JLabel] of bold and italic. */
    fun JLabel.plain(): JLabel {
        font = font.deriveFont(Font.PLAIN)
        return this
    }

    /** Sets a [JLabel] to bold. */
    fun JLabel.bold(): JLabel {
        font = font.deriveFont(font.style or Font.BOLD)
        return this
    }

    /** Sets a [JLabel] to italic. */
    fun JLabel.italic(): JLabel {
        font = font.deriveFont(font.style or Font.ITALIC)
        return this
    }

    fun JComponent.border(border: Border?): JComponent {
        this.border = border
        return this
    }

//    fun Border.inset(){
//
//    }

}