package com.slimtrade.gui.custom;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;

import javax.swing.*;
import java.awt.*;

public class CustomSlider extends JSlider implements IColorable {

    public CustomSlider() {
        this.setUI(new CustomSliderUI(this));
        this.setMinimum(0);
        this.setMaximum(100);
        this.setMajorTickSpacing(25);
        this.setMinorTickSpacing(5);
        this.setSnapToTicks(true);
        this.setPaintTicks(true);
    }

    @Override
    public void updateColor() {
        this.setBackground(ColorManager.BACKGROUND);
    }
}
