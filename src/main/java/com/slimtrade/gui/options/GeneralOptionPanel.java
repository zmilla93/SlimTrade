package com.slimtrade.gui.options;

import com.slimtrade.gui.options.general.BasicsPanel;

import javax.swing.*;
import java.awt.*;

public class GeneralOptionPanel extends AbstractOptionPanel {

    GridBagConstraints gc = new GridBagConstraints();

    public GeneralOptionPanel() {

        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1;
        gc.gridx = 0;
        gc.gridy = 0;

        BasicsPanel basicsPanel = new BasicsPanel();

        contentPanel.add(createHeader("Basics"), gc);
        gc.gridy++;
        contentPanel.add(basicsPanel, gc);
        gc.gridy++;
        contentPanel.add(Box.createVerticalStrut(10), gc);
        gc.gridy++;

    }

}
