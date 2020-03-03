package com.slimtrade.gui.tutorial.panels;

import com.slimtrade.gui.basic.CustomLabel;
import com.slimtrade.gui.basic.SectionHeader;

import javax.swing.*;

public class ScannerPanel extends AbstractTutorialPanel {

    private JLabel info1 = new CustomLabel("The chat scanner can be used to search for custom phrases.");
    private JLabel info2 = new CustomLabel("Multiple search presets can be made with custom responses for each.");
    private JLabel info3 = new CustomLabel("Chat scanner message are orange.");

    public ScannerPanel() {
        gc.insets.bottom = 5;
        container.add(new SectionHeader("Chat Scanner"), gc);
        gc.gridy++;
        gc.insets.bottom = 0;
        container.add(info1, gc);
        gc.gridy++;
        container.add(info2, gc);
        gc.gridy++;
        gc.insets.top = 10;
        container.add(new ImageLabel("images/scanner-message.png"), gc);

    }

}
