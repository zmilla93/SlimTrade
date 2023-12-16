package com.slimtrade.gui.options;

import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.core.trading.TradeOfferType;
import com.slimtrade.gui.components.PlainLabel;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.modules.theme.ThemeManager;

import javax.swing.*;

public class DebugOptionPanel extends AbstractOptionPanel {

    private final JButton incomingMessageButton = new JButton("Incoming Trade");
    private final JButton outgoingMessageButton = new JButton("Outgoing Trade");
    private final JButton scannerMessageButton = new JButton("Scanner Message");
    private final JButton updateMessageButton = new JButton("Update Message");
    private final JButton uiDump = new JButton("Dump UIManager to Clipboard");

    public DebugOptionPanel() {
        addHeader("Debug Tools");
        addComponent(incomingMessageButton);
        addComponent(outgoingMessageButton);
        addComponent(scannerMessageButton);
        addComponent(updateMessageButton);
        addComponent(uiDump);
        addVerticalStrut();
        addHeader("Font Test");
        addComponent(new PlainLabel("Almost before we knew it, we had left the ground."));
        addComponent(new JLabel("The quick brown fox jumped over the lazy dogs."));
        addComponent(new PlainLabel("You are captured, stupid beast!"));
        addComponent(new JLabel("You are captured, stupid beast!"));
        addListeners();
    }

    private void addListeners() {
        incomingMessageButton.addActionListener(e -> FrameManager.messageManager.addMessage(TradeOffer.getExampleTrade(TradeOfferType.INCOMING_TRADE)));
        outgoingMessageButton.addActionListener(e -> FrameManager.messageManager.addMessage(TradeOffer.getExampleTrade(TradeOfferType.OUTGOING_TRADE)));
        scannerMessageButton.addActionListener(e -> FrameManager.messageManager.addMessage(TradeOffer.getExampleTrade(TradeOfferType.CHAT_SCANNER_MESSAGE)));
        updateMessageButton.addActionListener(e -> FrameManager.messageManager.addUpdateMessage(true));
        uiDump.addActionListener(e -> ThemeManager.debugKeyValueDump());
    }

}
