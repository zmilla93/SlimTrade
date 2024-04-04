package com.slimtrade.gui.components;

import com.slimtrade.gui.buttons.IIcon;
import com.slimtrade.modules.theme.ThemeManager;

import javax.swing.*;

/**
 * Displays an icon that will auto resize when icon size is changed.
 */
public class BasicIconLabel extends JLabel {

    private final int BORDER_SIZE;
    protected final String iconPath;

    public BasicIconLabel(IIcon iconData) {
        this(iconData, 0);
    }

    public BasicIconLabel(IIcon iconData, int borderSize) {
        this.iconPath = iconData.path();
        this.BORDER_SIZE = borderSize;
        updateUI();
    }

    protected void applyIcon() {
        setIcon(ThemeManager.getIcon(iconPath));
    }

    @Override
    public void updateUI() {
        super.updateUI();
        if (iconPath != null) applyIcon();
        if (BORDER_SIZE > 0)
            setBorder(BorderFactory.createLineBorder(UIManager.getColor("Separator.foreground"), BORDER_SIZE));
    }

}
