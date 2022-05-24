package com.slimtrade.gui.components;

import javax.swing.*;
import java.awt.*;

/**
 * A panel that holds a checkbox and a label
 */
public class CheckBoxPanel extends JPanel {

    private JCheckBox checkBox = new JCheckBox();

    public CheckBoxPanel(String label) {
        setLayout(new BorderLayout());
        add(checkBox, BorderLayout.WEST);
        add(Box.createHorizontalStrut(5), BorderLayout.CENTER);
        add(new JLabel(label), BorderLayout.EAST);
    }

    public JCheckBox getCheckBox() {
        return checkBox;
    }

}
