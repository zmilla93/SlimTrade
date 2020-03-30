package com.slimtrade.gui.basic;

import com.slimtrade.core.managers.ColorManager;

import javax.swing.*;

public class CustomToolTip extends JToolTip {

    public CustomToolTip(JComponent component) {
        super();
        setComponent(component);
        setForeground(ColorManager.TEXT);
        setBackground(ColorManager.BACKGROUND);
        setBorder(BorderFactory.createLineBorder(ColorManager.TEXT));
    }

}
