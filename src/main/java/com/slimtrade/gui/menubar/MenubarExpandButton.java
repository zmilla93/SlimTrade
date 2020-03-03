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
        colorDefault = ColorManager.PRIMARY;
        colorHover = ColorManager.PRIMARY;
        colorPressed = ColorManager.PRIMARY;
        this.repaint();
    }

}
