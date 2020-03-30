package com.slimtrade.gui.custom;

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
    public JToolTip createToolTip() {
        return new CustomToolTip(this);
    }

    @Override
    public void updateColor() {
        this.setForeground(ColorManager.TEXT);
        createToolTip();
    }


}
