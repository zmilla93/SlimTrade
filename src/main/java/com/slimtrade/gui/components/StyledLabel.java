package com.slimtrade.gui.components;

import javax.swing.*;
import java.awt.*;

/**
 * A JLabel that allows setting bold, italic, and color.
 * Color can either be set directly, or using a key from the UIManager.
 */
public class StyledLabel extends JLabel {

    private enum ColorMode {
        COLOR, KEY
    }

    private boolean bold;
    private boolean italic;
    private String colorKey;
    private Color color;
    private ColorMode colorMode;

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

    public void setColorKey(String key) {
        this.colorKey = key;
        colorMode = ColorMode.KEY;
        updateColor();
    }

    public void setColor(Color color) {
        this.color = color;
        colorMode = ColorMode.COLOR;
        updateColor();
    }

    private void updateFont() {
        Font font = getFont();
        int mask = Font.PLAIN;
        if (bold) mask = mask | Font.BOLD;
        if (italic) mask = mask | Font.ITALIC;
        setFont(font.deriveFont(mask, font.getSize()));
    }

    private void updateColor() {
        if (colorMode == ColorMode.COLOR) {
            if (color == null) return;
            setForeground(color);
        } else if (colorMode == ColorMode.KEY) {
            if (colorKey == null) return;
            Color color = UIManager.getColor(colorKey);
            setForeground(color);
        }
    }

    @Override
    public void updateUI() {
        super.updateUI();
        updateFont();
        updateColor();
    }

}
