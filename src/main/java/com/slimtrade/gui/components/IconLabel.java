package com.slimtrade.gui.components;

import com.slimtrade.gui.buttons.IIcon;
import com.slimtrade.modules.theme.ThemeManager;

/**
 * A solid colored icon that will always match the current theme and resize when icon size is changed.
 */
public class IconLabel extends BasicIconLabel {

    public IconLabel(IIcon iconData) {
        super(iconData, 0);
    }

    public IconLabel(IIcon iconData, int borderSize) {
        super(iconData, borderSize);
    }

    @Override
    protected void applyIcon() {
        setIcon(ThemeManager.getColorIcon(iconPath));
    }

}
