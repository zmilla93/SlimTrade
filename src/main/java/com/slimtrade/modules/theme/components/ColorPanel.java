package com.slimtrade.modules.theme.components;

import javax.swing.*;
import java.awt.*;

/**
 * A panel that can have the background color set to a UIManager key using setBackgroundKey().
 * Can be given a multiplier to alter the color using setColorMultiplier().
 */
public class ColorPanel extends JPanel {

    private String key;
    private float colorMultiplier = 1;

    /**
     * See {@link ColorPanel} for info.
     */
    public ColorPanel() {

    }

    /**
     * See {@link ColorPanel} for info.
     */
    public ColorPanel(LayoutManager layoutManager) {
        super(layoutManager);
    }

    public void setBackgroundKey(String key) {
        this.key = key;
    }

    public void setColorMultiplier(float colorMultiplier) {
        if (colorMultiplier < 0) colorMultiplier = 0;
        if (colorMultiplier > 10) colorMultiplier = 10;
        this.colorMultiplier = colorMultiplier;
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
