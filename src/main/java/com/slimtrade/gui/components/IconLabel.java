package com.slimtrade.gui.components;

import com.slimtrade.core.utility.ColorManager;

import javax.swing.*;

public class IconLabel extends JLabel {

    public IconLabel(String path) {
        setIcon(ColorManager.getIcon(path));
    }

    public IconLabel(String path, int size) {
        setIcon(ColorManager.getIcon(path, size));
    }

    public IconLabel(String path, boolean fromDisk) {
        setIcon(ColorManager.getIcon(path, -1, false));
    }

}
