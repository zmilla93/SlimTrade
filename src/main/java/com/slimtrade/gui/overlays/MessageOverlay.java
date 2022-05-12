package com.slimtrade.gui.overlays;

import com.slimtrade.gui.messaging.OverlayTradeMessagePanel;
import com.slimtrade.gui.windows.AbstractMovableDialog;

import java.awt.*;

public class MessageOverlay extends AbstractMovableDialog {

    public MessageOverlay() {

//        TradeMessagePanel messagePanel = new TradeMessagePanel(TradeOffer.getExampleTrade(TradeOffer.TradeOfferType.INCOMING), false);

        OverlayTradeMessagePanel infoPanel = new OverlayTradeMessagePanel();
        setOpacity(0.75f);
        container.setBackground(Color.orange);
        container.setLayout(new BorderLayout());
//        getGlassPane().setBackground(new Color(1, 0, 0, 0.5f));
//        getGlassPane().setBackground(new Color(255, 0, 0));

//        messagePanel.setVisible(false);
//        container.add(messagePanel, BorderLayout.CENTER);
        container.add(infoPanel, BorderLayout.CENTER);


        pack();
        setLocation(0, 0);
    }

}
