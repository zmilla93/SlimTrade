package com.slimtrade.gui.options;

import com.slimtrade.core.utility.GUIReferences;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.components.PlainLabel;

import javax.swing.*;
import java.awt.*;

/**
 * A header label + Separator.
 *
 * @see AbstractOptionPanel
 */
public class HeaderPanel extends JPanel {

    private final JLabel label;

    public HeaderPanel(String title) {
        setLayout(new GridBagLayout());
        JPanel labelPanel = new JPanel(new BorderLayout());
        GridBagConstraints headerGC = ZUtil.getGC();
        headerGC.insets = new Insets(0, GUIReferences.INSET, 0, 0);
        label = new JLabel(title);
        Color col = UIManager.getColor("Label.foreground");
        label.setForeground(col);
        labelPanel.add(label, BorderLayout.CENTER);
        headerGC.weightx = 1;
        headerGC.fill = GridBagConstraints.HORIZONTAL;
        add(labelPanel, headerGC);
        headerGC.gridy++;
        JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
        headerGC.fill = GridBagConstraints.BOTH;
        headerGC.insets = new Insets(0, 10, 0, 10);
        add(sep, headerGC);
//        int prevFill = gc.fill;
//        double prevWeightX = gc.weightx;
//        gc.fill = GridBagConstraints.HORIZONTAL;
//        gc.weightx = 1;
//        contentPanel.add(panel, gc);
//        gc.fill = prevFill;
//        gc.weightx = prevWeightX;
//        gc.gridy++;
//        return panel;
    }

    public JLabel getLabel() {
        return label;
    }

}
