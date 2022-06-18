package com.slimtrade.gui.options;

import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.core.trading.TradeOfferType;
import com.slimtrade.core.utility.ColorManager;
import com.slimtrade.gui.managers.FrameManager;

import javax.swing.*;

public class DebugOptionPanel extends AbstractOptionPanel {

    private final JButton incomingMessageButton = new JButton("Incoming Trade");
    private final JButton outgoingMessageButton = new JButton("Outgoing Trade");
    private final JButton scannerMessageButton = new JButton("Scanner Message");
    private final JButton updateMessageButton = new JButton("Update Message");
    private final JButton uiDump = new JButton("Dump UIManager to Clipboard");

    public DebugOptionPanel() {
        addHeader("Debug Tools");
        addPanel(incomingMessageButton);
        addPanel(outgoingMessageButton);
        addPanel(scannerMessageButton);
        addPanel(updateMessageButton);
        addPanel(uiDump);
        addVerticalStrut();
        addHeader("Font Test");
        addPanel(new JLabel("Almost before we knew it, we had left the ground."));
        addPanel(new JLabel("The quick brown fox jumped over the lazy dogs."));
        addListeners();
    }

    private void addListeners() {
        incomingMessageButton.addActionListener(e -> FrameManager.messageManager.addMessage(TradeOffer.getExampleTrade(TradeOfferType.INCOMING_TRADE)));
        outgoingMessageButton.addActionListener(e -> FrameManager.messageManager.addMessage(TradeOffer.getExampleTrade(TradeOfferType.OUTGOING_TRADE)));
        scannerMessageButton.addActionListener(e -> FrameManager.messageManager.addMessage(TradeOffer.getExampleTrade(TradeOfferType.CHAT_SCANNER_MESSAGE)));
        updateMessageButton.addActionListener(e -> FrameManager.messageManager.addUpdateMessage(true));
        uiDump.addActionListener(e -> ColorManager.debugKeyValueDump());
    }

}
