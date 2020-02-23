package com.slimtrade.gui.tutorial.panels;

import com.slimtrade.gui.basic.CustomLabel;
import com.slimtrade.gui.basic.SectionHeader;

import javax.swing.*;

public class HistoryTutorialPanel extends AbstractTutorialPanel {

    private JLabel info1 = new CustomLabel("The trade history can be used reopen recent trade popups.");
    private JLabel info2 = new CustomLabel("It loads using the client file, so it will even contain trades from when SlimTrade wasn't running.");

    public HistoryTutorialPanel() {

        gc.insets.bottom = 5;
        container.add(new SectionHeader("Trade History"), gc);
        gc.insets.bottom = 0;
        gc.gridy++;
        container.add(info1, gc);
        gc.gridy++;
        container.add(info2, gc);
        gc.gridy++;
        gc.insets.top = 10;
        container.add(new ImageLabel("images/history.png"), gc);

    }

}
