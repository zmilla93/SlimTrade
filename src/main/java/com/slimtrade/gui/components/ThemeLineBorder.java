package com.slimtrade.gui.components;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Works like a line border, but allows setting color via a UIManager key.
 */
public class ThemeLineBorder implements Border {

    private static final String DEFAULT_COLOR_KEY = "Separator.foreground";
    private static final int DEFAULT_BORDER_SIZE = 1;

    private final int borderSizeTop;
    private final int borderSizeLeft;
    private final int borderSizeBottom;
    private final int borderSizeRight;
    private final String colorKey;
    private final Insets insets;

    public ThemeLineBorder() {
        this(DEFAULT_BORDER_SIZE);
    }

    public ThemeLineBorder(int thickness) {
        this(thickness, thickness, thickness, thickness);
    }

    public ThemeLineBorder(int top, int left, int bottom, int right) {
        this.colorKey = DEFAULT_COLOR_KEY;
        borderSizeTop = top;
        borderSizeLeft = left;
        borderSizeBottom = bottom;
        borderSizeRight = right;
        insets = new Insets(top, left, bottom, right);
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(UIManager.getColor(colorKey));
        g.fillRect(0, 0, width, borderSizeTop);                             // Top
        g.fillRect(0, height - borderSizeBottom, width, borderSizeBottom);  // Bottom
        g.fillRect(0, 0, borderSizeLeft, height);                           // Left
        g.fillRect(width - borderSizeRight, 0, borderSizeRight, height);    // Right
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return insets;
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }

}
