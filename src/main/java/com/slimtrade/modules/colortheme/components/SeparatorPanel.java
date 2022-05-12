package com.slimtrade.modules.colortheme.components;

import javax.swing.*;
import java.awt.*;

/**
 * Can be used with a BorderLayout panel to create a separator.
 */
public class SeparatorPanel extends JPanel {

    public SeparatorPanel() {
        this(1);
    }

    public SeparatorPanel(int size) {
        setPreferredSize(new Dimension(size, size));
        updateUI();
    }

    @Override
    public void updateUI() {
        super.updateUI();
        setBackground(UIManager.getColor("Separator.foreground"));
    }
}
