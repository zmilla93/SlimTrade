package github.zmilla93.core.poe

import github.zmilla93.core.poe.ScaledRect.getPercentRect
import java.awt.Rectangle
import java.awt.geom.Rectangle2D

object ScaledRect {

    private val COMPARISON_RECT = Rectangle(0, 0, 1920, 1080)

    /**
     * Takes in a rect with an absolute size relative to the original screen size
     * used for measurements. Returns a percentage size that can be used to scale
     * another UI element.
     */
    fun getPercentRect(
        unscaledRect: Rectangle,
        originalComparisonRect: Rectangle = COMPARISON_RECT
    ): Rectangle2D.Float {
        val percentX = ScaledInt.getPercentValue(unscaledRect.x, originalComparisonRect.width)
        val percentY = ScaledInt.getPercentValue(unscaledRect.y, originalComparisonRect.height)
        val percentWidth = ScaledInt.getPercentValue(unscaledRect.width, originalComparisonRect.width)
        val percentHeight = ScaledInt.getPercentValue(unscaledRect.height, originalComparisonRect.height)
        return Rectangle2D.Float(percentX, percentY, percentWidth, percentHeight)
    }

    /**
     * Takes in a [getPercentRect], as well as the current screen size,
     * and returns a rect scaled to the current screen size.
     */
    fun getScaledRect(
        percentageRect: Rectangle2D.Float,
        currentComparisonRect: Rectangle = POEWindow.gameBounds
    ): Rectangle {
        val scaledX = ScaledInt.getScaledValue(percentageRect.x, currentComparisonRect.width) + currentComparisonRect.x
        val scaledY = ScaledInt.getScaledValue(percentageRect.y, currentComparisonRect.height) + currentComparisonRect.y

        /** NOTE : The width is scaled by the height comparison height instead of width,
         * because UI elements don't stretch horizontally, just their position is scaled. */
        // FIXME : Double check that this is ALWAYS the correct way to scale rects, and not just the stash.
        val scaledWidth = ScaledInt.getScaledValue(percentageRect.height, currentComparisonRect.height)
        val scaledHeight = ScaledInt.getScaledValue(percentageRect.height, currentComparisonRect.height)
        return Rectangle(scaledX, scaledY, scaledWidth, scaledHeight)
    }
}
