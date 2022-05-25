package com.slimtrade.gui.options;

import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.gui.managers.FrameManager;

import javax.swing.*;

public class DebugOptionPanel extends AbstractOptionPanel {

    private final JButton incomingMessageButton = new JButton("Incoming Trade");
    private final JButton outgoingMessageButton = new JButton("Outgoing Trade");
    private final JButton scannerMessageButton = new JButton("Scanner Message");
    private final JButton updateMessageButton = new JButton("Update Message");

    public DebugOptionPanel() {
        addHeader("Debug Tools");
        addPanel(incomingMessageButton);
        addPanel(outgoingMessageButton);
        addPanel(scannerMessageButton);
        addPanel(updateMessageButton);
        addListeners();
    }

    private void addListeners() {
        incomingMessageButton.addActionListener(e -> FrameManager.messageManager.addMessage(TradeOffer.getExampleTrade(TradeOffer.TradeOfferType.INCOMING)));
        outgoingMessageButton.addActionListener(e -> FrameManager.messageManager.addMessage(TradeOffer.getExampleTrade(TradeOffer.TradeOfferType.OUTGOING)));
        scannerMessageButton.addActionListener(e -> FrameManager.messageManager.addMessage(TradeOffer.getExampleTrade(TradeOffer.TradeOfferType.CHAT_SCANNER)));
        updateMessageButton.addActionListener(e -> FrameManager.messageManager.addUpdateMessage(true));
    }

}
