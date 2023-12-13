package com.slimtrade.gui.chatscanner;

import com.slimtrade.gui.components.PlainLabel;
import com.slimtrade.gui.options.AbstractOptionPanel;

import javax.swing.*;

public class ChatScannerInfoPanel extends AbstractOptionPanel {

    public ChatScannerInfoPanel() {
        super(true);
        addHeader("Chat Scanner Info");
        addPanel(new PlainLabel("Allows you to search for custom phrases in chat."));
        addPanel(new PlainLabel("Once you create an entry, select it from the list then click 'Start Scanning'."));
        addPanel(new PlainLabel("Use CTRL or SHIFT to select multiple entries for scanning."));
        addPanel(new JLabel("Click 'New Entry' in the lower left to get started!"));
    }

}
