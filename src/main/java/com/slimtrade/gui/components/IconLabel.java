package com.slimtrade.gui.components;

import com.slimtrade.modules.theme.ThemeManager;

import javax.swing.*;

@Deprecated
public class IconLabel extends JLabel {

    public IconLabel(String path) {
        setIcon(ThemeManager.getIcon(path));
    }

    public IconLabel(String path, int size) {
        setIcon(ThemeManager.getIcon(path, size));
    }

    public IconLabel(String path, boolean fromDisk) {
        setIcon(ThemeManager.getIcon(path, -1, false));
    }

}
