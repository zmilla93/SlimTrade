package com.slimtrade.gui.basic;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;

import javax.swing.*;

public class CustomCheckbox extends JCheckBox implements IColorable {

    public CustomCheckbox() {
//        super();
        this.setFocusPainted(false);
        this.setIcon(new CustomCheckboxIcon());
    }

    @Override
    public void updateColor() {
        this.setBackground(ColorManager.BACKGROUND);
    }
}
