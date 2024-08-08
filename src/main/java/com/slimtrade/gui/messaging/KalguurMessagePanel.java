package com.slimtrade.gui.messaging;

import java.awt.*;

public class KalguurMessagePanel extends NotificationPanel {

    public KalguurMessagePanel() {
        closeButtonInTopRow = false;
        topContainer.setVisible(false);
        itemButton.setText("Shipment Complete!");
        setup();
    }

    @Override
    protected void resolveMessageColor() {
        messageColor = new Color(47, 141, 176);
    }

}
