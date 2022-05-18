package com.slimtrade.modules.colortheme.components;

import javax.swing.*;
import java.awt.*;

/**
 * Sets a panels background color using a UIManager key.
 */
public class ColorPanel extends JPanel {

    private String key;
    public float colorMultiplier = 1;

    public ColorPanel() {

    }

    public ColorPanel(LayoutManager layoutManager) {
        super(layoutManager);
    }

    public ColorPanel(String key) {
        this.key = key;
    }

    public void setBackgroundKey(String key) {
        this.key = key;
    }

    @Override
    public void updateUI() {
        super.updateUI();
        if (key == null) return;
        Color color = UIManager.getColor(key);
        if (color == null) return;
        if (colorMultiplier != 1) {
            color = new Color(applyRange(0, 255, color.getRed() * colorMultiplier),
                    applyRange(0, 255, color.getGreen() * colorMultiplier),
                    applyRange(0, 255, color.getBlue() * colorMultiplier));

        }
        setBackground(color);
    }

    private int applyRange(int min, int max, float value) {
        if (value < min) return min;
        if (value > max) return max;
        return (int) Math.floor(value);
    }
}
