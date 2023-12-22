package com.slimtrade.gui.components;

import com.slimtrade.modules.theme.ThemeColorVariant;
import com.slimtrade.modules.theme.ThemeColorVariantSetting;

import javax.swing.*;
import java.awt.*;

/**
 * A JLabel that allows setting bold, italic, and color.
 * Color can either be set directly, using a key from the UIManager.
 *
 * @see ThemedStyleLabel
 */
public class StyledLabel extends JLabel {

    protected enum ColorMode {
        COLOR, KEY, VARIANT
    }

    private boolean bold;
    private boolean italic;
    private String colorKey;
    private Color color;
    protected ThemeColorVariantSetting colorVariant;
    protected ColorMode colorMode;

    public StyledLabel() {
        super();
        updateFont();
    }

    public StyledLabel(String text) {
        super(text);
        updateFont();
    }

    public void setBold(boolean bold) {
        this.bold = bold;
        updateFont();
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
        updateFont();
    }

    /**
     * Sets the text color directly. This is the same as using setForeground().
     *
     * @param color Target color
     */
    public void setColor(Color color) {
        this.color = color;
        colorMode = ColorMode.COLOR;
        updateColor();
    }

    /**
     * Set text color using a UIManager key.
     *
     * @param key UIManager color key
     */
    public void setColorKey(String key) {
        this.colorKey = key;
        colorMode = ColorMode.KEY;
        updateColor();
    }

    private void updateFont() {
        Font font = getFont();
        int mask = Font.PLAIN;
        if (bold) mask = mask | Font.BOLD;
        if (italic) mask = mask | Font.ITALIC;
        setFont(font.deriveFont(mask, font.getSize()));
    }

    protected void updateColor() {
        if (colorMode == ColorMode.COLOR) {
            if (color == null) return;
            super.setForeground(color);
        } else if (colorMode == ColorMode.KEY) {
            if (colorKey == null) return;
            Color color = UIManager.getColor(colorKey);
            super.setForeground(color);
        } else if (colorMode == ColorMode.VARIANT) {
            Color color = ThemeColorVariant.getColorVariant(colorVariant.variant(), colorVariant.opposite(), colorVariant.colorBlind());
            super.setForeground(color);
        }
    }

    @Override
    public void updateUI() {
        super.updateUI();
        updateFont();
        updateColor();
    }

    @Override
    public void setForeground(Color fg) {
        setColor(fg);
    }

}
