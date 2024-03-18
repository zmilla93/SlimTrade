package com.slimtrade.gui.buttons;

import com.slimtrade.modules.theme.ThemeManager;

import javax.swing.*;

/**
 * A resizable icon button that automatically recolors the icon to match the current theme.
 */
public class IconButton extends BasicIconButton {

    public IconButton(IIcon icon) {
        super(icon);
    }

    public IconButton(IIcon icon, int size) {
        super(icon, size);
    }

    @Override
    protected Icon createIcon() {
        return ThemeManager.getColorIcon(path, size);
    }

}
