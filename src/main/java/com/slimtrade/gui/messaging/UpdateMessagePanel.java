package com.slimtrade.gui.messaging;

import com.slimtrade.App;

public class UpdateMessagePanel extends NotificationPanel {

    public UpdateMessagePanel() {
        pricePanel.setVisible(false);
        playerNameButton.setText("SlimTrade Update Available!");
        itemButton.setText("Click here to install now, or restart the app later.");
        playerNameButton.addActionListener(e -> App.updateManager.runUpdateProcessFromSwing());
        itemButton.addActionListener(e -> App.updateManager.runUpdateProcessFromSwing());
        setup();
    }

    @Override
    protected void resolveMessageColor() {
        // TODO
    }

}
