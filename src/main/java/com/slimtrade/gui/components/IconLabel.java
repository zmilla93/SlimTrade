package com.slimtrade.gui.components;

import com.slimtrade.gui.buttons.IIcon;
import com.slimtrade.modules.theme.ThemeManager;

import javax.swing.*;

/**
 * Displays a solid colored icon that will always match the current theme.
 */
public class IconLabel extends JLabel {

    private final int BORDER_SIZE;
    private final String iconPath;

    public IconLabel(IIcon iconData) {
        this(iconData, 0);
    }

    public IconLabel(IIcon iconData, int borderSize) {
        this.iconPath = iconData.path();
        this.BORDER_SIZE = borderSize;
        updateUI();
    }

    @Override
    public void updateUI() {
        super.updateUI();
        if (iconPath == null) return;
        setIcon(ThemeManager.getColorIcon(iconPath));
        if (BORDER_SIZE <= 0) return;
        setBorder(BorderFactory.createLineBorder(UIManager.getColor("Separator.foreground"), BORDER_SIZE));
    }

}
