package com.slimtrade.gui.basic;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;

import javax.swing.*;

public class CustomLabel extends JLabel implements IColorable {

    public CustomLabel() {
        super();
    }

    public CustomLabel(String text) {
        super(text);
    }

    @Override
    public void updateColor() {
        this.setForeground(ColorManager.TEXT);
    }
}
