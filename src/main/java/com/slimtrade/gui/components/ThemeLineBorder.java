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

    private final int borderSize;
    private final String colorKey;
    private final Insets insets;

    public ThemeLineBorder() {
        this.borderSize = DEFAULT_BORDER_SIZE;
        this.colorKey = DEFAULT_COLOR_KEY;
        insets = new Insets(borderSize, borderSize, borderSize, borderSize);
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
        return insets;
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }

}
