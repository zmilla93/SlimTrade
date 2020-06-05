package com.slimtrade.gui.buttons;

import com.slimtrade.core.managers.ColorManager;

import java.awt.*;

public class DenyButton extends BasicButton {

    private static final long serialVersionUID = 1L;

    public DenyButton(String text) {
        super(text);
        primaryColor = ColorManager.RED_DENY;
        secondaryColor = ColorManager.BACKGROUND;
        this.updateColor();
    }

    @Override
    protected void paintComponent(Graphics g) {
        primaryColor = ColorManager.RED_DENY;
        super.paintComponent(g);
    }

}
