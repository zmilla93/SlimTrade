package io.github.zmilla93.modules.theme

import github.zmilla93.modules.zswing.uiproperty.IColor
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel

/**
 * Allows components to store properties that get reapplied or reevaluated on theme change.
 * Properties are applied via extension functions, which support function chaining.
 *
 * There are specific extensions for common components like JLabel and JButton,
 * as well as a generic JComponent<T> version for less common ones.
 */
@Suppress("UNCHECKED_CAST")
enum class UIProperty(val runner: UIPropertyApplier<Any>) {

    // Applying values to UI components (Setter) is defined here
    FONT_SIZE({ comp, value -> comp.font = comp.font.deriveFont(value as Float) }),
    FONT_COLOR({ comp, value -> comp.foreground = (value as IColor).color() }),
    BACKGROUND_COLOR({ comp, value -> comp.background = (value as IColor).color() }),
    ;

    // TODO @idea : Rather that storing a single field, store an object of all fields?
    //  This stores slightly more (unused) data per obj, but cuts down on checks per UIObject.
    //  This only makes sense if the number of fields grows too large. Seems more complicated though.
    companion object {

        /** Apply a [UIProperty] [value] to a UI [comp] */
        fun <T : JComponent, U> applyProperty(comp: T, prop: UIProperty, value: U): T {
            prop.runner.applyProperty(comp, value as Any)
            return comp
        }

        /** Store a [UIProperty] [value] in a UI [comp]]  */
        fun <T : JComponent, U> putProperty(comp: T, prop: UIProperty, value: U): T {
            comp.putClientProperty(prop, value)
            applyProperty(comp, prop, value)
            return comp
        }

        /** Get a [UIProperty] value from a UI [comp]. */
        fun <T> getProperty(comp: JComponent, prop: UIProperty): T? {
            return comp.getClientProperty(prop) as T
        }

    }

    /** Font Size */
    object FontSizeExtensions {

        fun <T> JComponent.fontSize(size: Int): T {
            return applyFontSize(this, size)
        }

        fun JLabel.fontSize(size: Int): JLabel {
            return applyFontSize(this, size)
        }

        fun JButton.fontSize(size: Int): JButton {
            return applyFontSize(this, size)
        }

        private fun <T> applyFontSize(comp: JComponent, size: Int): T {
            val fontSize = size.toFloat()
            comp.font = comp.font.deriveFont(fontSize)
            return UIProperty.putProperty(comp, FONT_SIZE, fontSize) as T
        }

    }

    /** Font Color */
    object FontColorExtensions {

        fun <T> JComponent.textColor(color: IColor): T {
            return applyTextColor(this, color)
        }

        fun JLabel.textColor(color: IColor): JLabel {
            return applyTextColor(this, color)
        }

        fun JButton.textColor(color: IColor): JButton {
            return applyTextColor(this, color)
        }

        @Deprecated("Switch to label, button, or generic version once LabelPanel is removed")
        fun JComponent.textColor(color: IColor): JComponent {
            // FIXME : Remove this check once LabelPanel has been removed.
//            if (this is LabelPanel) {
//                UIProperty.putProperty(this.label, FONT_COLOR, color)
//                return this
//            }
            return UIProperty.putProperty(this, FONT_COLOR, color)
        }

        private fun <T> applyTextColor(comp: JComponent, color: IColor): T {
            return UIProperty.putProperty(comp, FONT_COLOR, color) as T
        }

    }

    /** Background Color */
    object BackgroundColorExtensions {

        fun <T> JComponent.background(color: IColor): T {
            return applyBackgroundColor(this, color)
        }

        fun JPanel.background(color: IColor): JPanel {
            return applyBackgroundColor(this, color)
        }

        fun JLabel.background(color: IColor): JLabel {
            return applyBackgroundColor(this, color)
        }

        fun JButton.background(color: IColor): JButton {
            return applyBackgroundColor(this, color)
        }

        fun <T> applyBackgroundColor(comp: JComponent, color: IColor): T {
            comp.isOpaque = true
            return UIProperty.putProperty(comp, BACKGROUND_COLOR, color) as T
        }

    }

}