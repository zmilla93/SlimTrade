package com.slimtrade.gui.components;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Similar to a line border, but has an inset size of 0.
 * This means the border will not increase the size of the component it is attached to.
 * Takes a key for the UIManager to set the color.
 */
public class InsetBorder implements Border {

    private static final Insets EMPTY_INSETS = new Insets(0, 0, 0, 0);
    private static final String DEFAULT_COLOR_KEY = "Separator.foreground";
    private static final int DEFAULT_BORDER_SIZE = 1;

    private final int borderSize;
    private final String colorKey;

    public InsetBorder() {
        this.colorKey = DEFAULT_COLOR_KEY;
        this.borderSize = DEFAULT_BORDER_SIZE;
    }

    public InsetBorder(int borderSize) {
        this.colorKey = DEFAULT_COLOR_KEY;
        this.borderSize = borderSize;
    }

    public InsetBorder(String colorKey) {
        this.colorKey = colorKey;
        this.borderSize = DEFAULT_BORDER_SIZE;
    }

    public InsetBorder(int borderSize, String colorKey) {
        this.borderSize = borderSize;
        this.colorKey = colorKey;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(UIManager.getColor(colorKey));
        g.fillRect(0, 0, width, borderSize);                    // Top
        g.fillRect(0, height - borderSize, width, borderSize);  // Bottom
        g.fillRect(0, 0, borderSize, height);                   // Left
        g.fillRect(width - borderSize, 0, borderSize, height);  // Right
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return EMPTY_INSETS;
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }

}
