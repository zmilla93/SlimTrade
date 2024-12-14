package github.zmilla93.core.poe;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class ScaledRect {

    public static Rectangle2D.Float getPercentRect(Rectangle unscaledRect, Rectangle originalComparisonRect) {
        float percentX = ScaledInt.getPercentValue(unscaledRect.x, originalComparisonRect.width);
        float percentY = ScaledInt.getPercentValue(unscaledRect.y, originalComparisonRect.height);
        float percentWidth = ScaledInt.getPercentValue(unscaledRect.width, originalComparisonRect.width);
        float percentHeight = ScaledInt.getPercentValue(unscaledRect.height, originalComparisonRect.height);
        return new Rectangle2D.Float(percentX, percentY, percentWidth, percentHeight);
    }

    public static Rectangle getScaledRect(Rectangle2D.Float percentageRect, Rectangle currentComparisonRect) {
        int scaledX = ScaledInt.getScaledValue(percentageRect.x, currentComparisonRect.width) + currentComparisonRect.x;
        int scaledY = ScaledInt.getScaledValue(percentageRect.y, currentComparisonRect.height) + currentComparisonRect.y;
        /// NOTE : The width is scaled by the height comparison height instead of width,
        ///        because UI elements don't stretch horizontally, just their position is scaled.
        // FIXME : Double check that this is ALWAYS the correct way to scale rects, and not just the stash.
        int scaledWidth = ScaledInt.getScaledValue(percentageRect.height, currentComparisonRect.height);
        int scaledHeight = ScaledInt.getScaledValue(percentageRect.height, currentComparisonRect.height);
        return new Rectangle(scaledX, scaledY, scaledWidth, scaledHeight);
    }

}
