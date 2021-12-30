package com.slimtrade.gui.messaging;

import javax.swing.*;
import java.awt.*;

public class NotificationTextButton extends JButton {

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
