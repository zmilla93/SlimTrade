package com.slimtrade.gui.tutorial.panels;

import com.slimtrade.gui.custom.CustomLabel;
import com.slimtrade.gui.basic.SectionHeader;

import javax.swing.*;

public class TradePanel extends AbstractTutorialPanel {

    JLabel info1 = new CustomLabel("Popups are created when a trade message is sent or received.");
    JLabel info2 = new CustomLabel("Buttons can be customized in the options window, where preset macros are also explained.");
    JLabel info3 = new CustomLabel("Incoming trades are green, outgoing trades are red.");

    public TradePanel() {
        gc.insets.bottom = 5;
        container.add(new SectionHeader("Trade Messages"), gc);
        gc.gridy++;
        gc.insets.bottom = 0;
        container.add(info1, gc);
        gc.gridy++;
        gc.insets.bottom = 5;
        container.add(info2, gc);
        gc.gridy++;

        container.add(new ImageLabel("images/incoming-trade.png"), gc);
        gc.gridy++;
        container.add(new ImageLabel("images/outgoing-trade.png"), gc);
        gc.gridy++;
        container.add(info3, gc);
    }

}
