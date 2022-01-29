package com.slimtrade.gui.options.general;

import com.slimtrade.core.utility.ZUtil;

import javax.swing.*;
import java.awt.*;

public class EnableFeaturesPanel extends JPanel {

    GridBagConstraints gc = ZUtil.getGC();

    public EnableFeaturesPanel() {
        setLayout(new GridBagLayout());
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1;

        JCheckBox incomingMessages = new JCheckBox();
        JCheckBox outgoingMessages = new JCheckBox();
        JCheckBox itemHighlighter = new JCheckBox();
        JCheckBox menubarButton = new JCheckBox();
        JCheckBox autoUpdateCheckbox = new JCheckBox();

        addRow("Incoming Messages", incomingMessages);
        addRow("Outgoing Messages", outgoingMessages);
        addRow("Item Highlighter", itemHighlighter);
        addRow("Menubar Button", menubarButton);
        addRow("Update Automatically", autoUpdateCheckbox);
    }

    private void addRow(String title, JComponent component) {
        gc.insets = new Insets(0, 10, 2, 0);
        add(component, gc);
        gc.gridx++;
        add(new JLabel(title), gc);
        gc.gridx = 0;
        gc.gridy++;
    }

}
