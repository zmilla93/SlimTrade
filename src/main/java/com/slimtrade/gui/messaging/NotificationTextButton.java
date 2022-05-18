package com.slimtrade.gui.messaging;

import com.slimtrade.modules.colortheme.components.AdvancedButton;

import javax.swing.*;
import java.awt.*;

public class NotificationTextButton extends AdvancedButton {

    public NotificationTextButton(String text) {
        super(text);
        updateUI();
    }

    @Override
    public void updateUI() {
        if (getFont() != null) {
            Font font = getFont().deriveFont(Font.BOLD);
            setFont(font);
        }
        super.updateUI();
        int INSET = 4;
        setBorder(BorderFactory.createEmptyBorder(0, INSET, 0, INSET));
    }

}
