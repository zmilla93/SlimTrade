package com.slimtrade.gui.options.general;

import javax.swing.*;
import java.awt.*;

public class LabelComponentPanel extends JPanel {

    public LabelComponentPanel(JLabel label, Component component) {
        super(new GridBagLayout());
        this.setOpaque(false);

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1;

        gc.anchor = GridBagConstraints.WEST;
        this.add(label, gc);
        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST;
        this.add(component, gc);

    }

    public LabelComponentPanel(JLabel label, Component component, int buffer) {
        super(new GridBagLayout());
        this.setOpaque(false);

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        this.add(label, gc);
        gc.gridx++;
        gc.insets.left = buffer;
        this.add(component, gc);

    }

}
