package com.slimtrade.gui.messaging;

import com.slimtrade.modules.theme.components.AdvancedButton;

import javax.swing.*;

public class NotificationButton extends AdvancedButton {

    public static final int INSET = 2;
    private int horizontalInset = -1;

    public NotificationButton(String text) {
        super();
        setText(text);
        updateUI();
    }

    public void setHorizontalInset(int inset) {
        horizontalInset = inset;
        updateUI();
    }

    @Override
    public void updateUI() {
        super.updateUI();
        int horizontal = horizontalInset == -1 ? INSET : horizontalInset;
        setBorder(BorderFactory.createEmptyBorder(INSET, horizontal, INSET, horizontal));
    }

}
