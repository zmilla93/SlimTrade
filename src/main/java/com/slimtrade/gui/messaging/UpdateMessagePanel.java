package com.slimtrade.gui.messaging;

public class UpdateMessagePanel extends NotificationPanel{

    public UpdateMessagePanel(){
        pricePanel.setVisible(false);
        playerNameButton.setText("SlimTrade Update Available!");
        itemButton.setText("Click here to install now, or restart the app later.");
        setup();
    }

}
