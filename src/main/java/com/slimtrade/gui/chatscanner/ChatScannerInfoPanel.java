package com.slimtrade.gui.chatscanner;

import com.slimtrade.gui.options.AbstractOptionPanel;

import javax.swing.*;

public class ChatScannerInfoPanel extends AbstractOptionPanel {

    public ChatScannerInfoPanel() {
        super(true);
        addHeader("Chat Scanner Info");
        addComponent(new JLabel("Allows you to search for custom phrases in chat."));
        addComponent(new JLabel("Once you create an entry, select it from the list then click 'Start Scanning'."));
        addComponent(new JLabel("Use CTRL or SHIFT to select multiple entries for scanning."));
        addComponent(new JLabel("Click 'New Entry' in the lower left to get started!"));
    }

}
