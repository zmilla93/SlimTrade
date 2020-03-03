package com.slimtrade.gui.buttons;

import com.slimtrade.core.managers.ColorManager;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;

public class CustomArrowButton extends BasicArrowButton {

    public CustomArrowButton(int direction) {
        super(direction, ColorManager.PRIMARY, ColorManager.LOW_CONTRAST_1, ColorManager.TEXT, ColorManager.LOW_CONTRAST_1);
        this.direction = direction;
        this.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
    }

    public void paint(Graphics g) {
        Color origColor;
        boolean isPressed, isEnabled;
        int w, h, size;

        w = getSize().width;
        h = getSize().height;
        origColor = g.getColor();
        isPressed = getModel().isPressed();
        isEnabled = isEnabled();
        g.setColor(getBackground());
        g.fillRect(0, 0, w, h);
        // If there's no room to draw arrow, bail
        if(h < 5 || w < 5)      {
            g.setColor(origColor);
            return;
        }
        // Translate arrow if pressed
        if (isPressed) {
            g.translate(1, 1);
        }
        size = 5;
        w = w/2;
        h = h/2;
        paintTriangle(g, w-(size/2), h-(size/2), size, direction, isEnabled);
        // Reset the Graphics back to original settings
        if (isPressed) {
            g.translate(-1, -1);
        }
        g.setColor(origColor);
    }

}
