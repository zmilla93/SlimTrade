package com.slimtrade.gui.menubar;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.enums.PreloadedImage;

public class MenubarExpandButton extends IconButton {

    public MenubarExpandButton() {
        super(PreloadedImage.TAG.getImage(), MenubarButton.HEIGHT);

    }

    @Override
    public void updateColor() {
        colorDefault = ColorManager.MENUBAR_EXPAND_BUTTON;
        colorHover = ColorManager.MENUBAR_EXPAND_BUTTON;
        colorPressed = ColorManager.MENUBAR_EXPAND_BUTTON;
        this.repaint();
    }

}
