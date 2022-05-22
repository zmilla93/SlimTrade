package com.slimtrade.gui.options;

import com.slimtrade.gui.options.ignore.IgnoreInputPanel;

import javax.swing.*;

public class IgnoreItemOptionPanel extends AbstractOptionPanel {

    public IgnoreItemOptionPanel() {
        addHeader("Ignore New Item");
        addPanel(new IgnoreInputPanel());
        addPanel(new JLabel("Set minutes to 0 to ignore indefinitely."));
        addPanel(new JLabel("Right click item names of incoming trades to ignore."));
        addVerticalStrut();
        addHeader("Ignore List");
//        addPanel();
    }

}
