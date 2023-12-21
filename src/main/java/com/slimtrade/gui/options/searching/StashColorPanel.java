package com.slimtrade.gui.options.searching;

import com.slimtrade.core.enums.StashTabColor;

import javax.swing.*;
import java.awt.*;

public class StashColorPanel extends JPanel {

    private final JLabel dummyLabel = new JLabel("Color");

    public StashColorPanel(int colorIndex) {
        setLayout(new BorderLayout());
        dummyLabel.setVisible(false);
        add(dummyLabel);
        setBackground(StashTabColor.values()[colorIndex].getBackground());
        updateUI();
    }

    @Override
    public void updateUI() {
        super.updateUI();
        if (dummyLabel == null) return;
        dummyLabel.setVisible(true);
        setPreferredSize(null);
        setPreferredSize(getPreferredSize());
        dummyLabel.setVisible(false);
    }
}
