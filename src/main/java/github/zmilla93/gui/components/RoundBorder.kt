package github.zmilla93.gui.components

import java.awt.*
import javax.swing.UIManager
import javax.swing.border.AbstractBorder

/**
 * A round border.
 */
class RoundBorder(
    private val color: Color? = null,
    private val thickness: Int = 1,
    private val paddingX: Int = 0,
    private val paddingY: Int = 0,
    private val solid: Boolean = false,
    private val arc: Int = DEFAULT_ARC,
) : AbstractBorder() {

    constructor(
        color: Color? = null,
        thickness: Int = 1,
        padding: Int = 0,
    ) : this(color, thickness, padding, padding)

    /** Required for a 1px border to show up with dynamic padding = 0 */
    val requiredPadding = 0

    companion object {
        const val DEFAULT_ARC = 10
    }

    override fun paintBorder(
        c: Component,
        g: Graphics,
        x: Int,
        y: Int,
        width: Int,
        height: Int
    ) {
        val g2 = g.create() as Graphics2D
        // FIXME : Check that this color works on all themes
        g2.color = color ?: UIManager.getColor("Button.startBorderColor")
        g2.stroke = BasicStroke(thickness.toFloat())
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        // FIXME : If this offset is correct, rename and clean up
        val offset = 0
        val offsetX2 = thickness
        if (solid) {
            g2.fillRoundRect(
                x + offset, y + offset,
                width - offsetX2, height - offsetX2,
                arc, arc
            )
        } else {
            g2.drawRoundRect(
                x + offset, y + offset,
                width - offsetX2, height - offsetX2,
                arc, arc
            )
        }
        g2.dispose()
    }

    override fun getBorderInsets(c: Component?): Insets? {
        val value = thickness + requiredPadding
        val x = value + paddingX
        val y = value + paddingY
        return Insets(y, x, y, x)
    }

//    override fun isBorderOpaque(): Boolean {
//        TODO("Not yet implemented")
//    }

//    override fun getBorderInsets(c: Component): Insets {
//        val value = thickness  + requiredPadding
//        val x = value + paddingX
//        val y = value + paddingY
//        return Insets(y,x,y,x)
//    }

//    override fun getBorderInsets(c: Component, insets: Insets): Insets {
//        val value = thickness  + requiredPadding
//        val x = value + paddingX
//        val y = value + paddingY
//        insets.set(value, value, value, value)
//        return insets
//    }

}