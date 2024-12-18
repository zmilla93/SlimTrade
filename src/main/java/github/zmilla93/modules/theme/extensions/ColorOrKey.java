package github.zmilla93.modules.theme.extensions;

import javax.swing.*;
import java.awt.*;

/**
 * A way of defining a color using either an actual Color, or using a UIManager color key.
 * Removes alpha channel unless otherwise specified.
 * It is important that the color is lazily instantiated when using a key, since the UIManager's
 * values are based on the current theme.
 */
// FIXME : This should probably also handle text color, but may not be required with adjusted foreground colors.
public class ColorOrKey {

    private Color color;
    private final String colorKey;

    public ColorOrKey(Color color) {
        this.color = color;
        this.colorKey = null;
    }

    public ColorOrKey(String colorKey) {
        this.colorKey = colorKey;
    }

    public Color resolve() {
        if (color == null) color = UIManager.getColor(colorKey);
        return color;
    }

}
