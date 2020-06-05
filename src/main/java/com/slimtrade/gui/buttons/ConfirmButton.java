package com.slimtrade.gui.buttons;

import com.slimtrade.core.managers.ColorManager;

import java.awt.*;

public class ConfirmButton extends BasicButton {

    private static final long serialVersionUID = 1L;

    public ConfirmButton(String text) {
        super(text);
        primaryColor = ColorManager.GREEN_APPROVE;
        secondaryColor = ColorManager.BACKGROUND;
    }

    @Override
    protected void paintComponent(Graphics g) {
        primaryColor = ColorManager.GREEN_APPROVE;
        super.paintComponent(g);
    }

}
