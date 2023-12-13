package com.slimtrade.gui.messaging;

import com.slimtrade.modules.colortheme.components.AdvancedButton;

import javax.swing.*;

public class ExpandPanel extends NotificationPanel {

    public ExpandPanel() {
        playerNameButton.setText("Show Messages");
        bottomContainer.setVisible(false);
        pricePanel.setVisible(false);
        setup();
        closeButton.setVisible(false);
        stopTimer();
        messageColor = UIManager.getColor("Button.foreground");
        borderPanel.setBackgroundKey("Label.foreground");
        applyMessageColor();
        updateSize();
    }

    public void setText(String text) {
        playerNameButton.setText(text);
    }

    public AdvancedButton getButton() {
        return playerNameButton;
    }

}
