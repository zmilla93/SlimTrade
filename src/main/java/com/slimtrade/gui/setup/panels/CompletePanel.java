package com.slimtrade.gui.setup.panels;

import com.slimtrade.gui.FrameManager;

import javax.swing.*;
import java.awt.*;

public class CompletePanel extends AbstractSetupPanel {

    private JLabel info1 = new JLabel("SlimTrade setup complete!");

    public CompletePanel() {
        this.add(info1, gc);
        gc.gridy++;
        this.add(Box.createHorizontalStrut(400), gc);
    }

}
