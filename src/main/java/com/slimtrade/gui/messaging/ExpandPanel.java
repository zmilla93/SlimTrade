package com.slimtrade.gui.messaging;

import com.slimtrade.modules.theme.components.AdvancedButton;

import javax.swing.*;

public class ExpandPanel extends NotificationPanel {

    public ExpandPanel() {
        playerNameButton.setText("Show Messages");
        bottomContainer.setVisible(false);
        pricePanel.setVisible(false);
        borderPanel.setBackgroundKey("Label.foreground");
        closeButton.setVisible(false);
        stopTimer();
        setup();
    }

    @Override
    protected void resolveMessageColor() {
        messageColor = UIManager.getColor("Button.foreground");
    }

    public void setText(String text) {
        playerNameButton.setText(text);
    }

    public AdvancedButton getButton() {
        return playerNameButton;
    }

}
