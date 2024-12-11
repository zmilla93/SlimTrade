package github.zmilla93.gui.messaging;

import github.zmilla93.core.utility.POEInterface;

import java.awt.*;

public class KalguurMessagePanel extends NotificationPanel {

    public KalguurMessagePanel() {
        closeButtonInTopRow = false;
        topContainer.setVisible(false);
        itemButton.setText("Shipment Complete!");
        itemButton.addActionListener(e -> POEInterface.pasteWithFocus("/kingsmarch"));
        setup();
    }

    @Override
    protected void resolveMessageColor() {
        messageColor = new Color(47, 141, 176);
    }

}
