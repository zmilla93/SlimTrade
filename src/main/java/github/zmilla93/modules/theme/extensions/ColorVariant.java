package github.zmilla93.modules.theme.extensions;

import java.awt.*;

/**
 * A color with an optional colorblind variant.
 */
public class ColorVariant {

    private ColorOrKey color;
    private ColorOrKey colorCB;

    // Basic Constructors

    public ColorVariant(ColorOrKey color) {
        this.color = color;
        this.colorCB = null;
    }

    public ColorVariant(ColorOrKey color, ColorOrKey colorCB) {
        this.color = color;
        this.colorCB = colorCB;
    }

    public ColorVariant(String color) {
        this(new ColorOrKey(color));
    }

    public ColorVariant(Color color) {
        this(new ColorOrKey(color));
    }

    public ColorVariant(String color, String colorCB) {
        this(new ColorOrKey(color), new ColorOrKey(colorCB));
    }

    public ColorVariant(Color color, Color colorCB) {
        this(new ColorOrKey(color), new ColorOrKey(colorCB));
    }

    // Getting/Setting

    public void setColor(ColorOrKey color) {
        this.color = color;
    }

    public void setColorCB(ColorOrKey colorCB) {
        this.colorCB = colorCB;
    }

    public void setColorblindColor(ColorOrKey colorCB) {
        this.colorCB = colorCB;
    }

    public boolean hasColorblindColor() {
        return this.colorCB != null;
    }

    public Color color() {
        return color.resolve();
    }

    public Color colorblindColor() {
        if (this.colorCB == null) return color.resolve();
        return colorCB.resolve();
    }

    public ColorOrKey getColorOrKey() {
        return color;
    }

    public ColorOrKey getColorOrKeyCB() {
        return colorCB;
    }

}
