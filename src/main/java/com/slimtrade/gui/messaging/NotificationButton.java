package com.slimtrade.gui.messaging;

import com.slimtrade.modules.colortheme.components.AdvancedButton;

import javax.swing.*;

public class NotificationButton extends AdvancedButton {

    public static final int INSET = 2;

    public NotificationButton(String text) {
        super();
        setText(text);
        updateUI();
    }

    @Override
    public void updateUI() {
        super.updateUI();
        setBorder(BorderFactory.createEmptyBorder(INSET, INSET, INSET, INSET));
    }

}
