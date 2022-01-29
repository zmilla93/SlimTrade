package com.slimtrade.gui.overlays;

import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.gui.windows.AbstractMovableDialog;

import javax.swing.*;
import java.awt.*;

public class OverlayInfoDialog extends AbstractMovableDialog {

    public OverlayInfoDialog() {
        super();
        JLabel label = new JLabel("Do some stuff!");

        container.setLayout(new BorderLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        int inset = 20;
        gc.insets = new Insets(inset, inset, inset, inset);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.red, 4));
        panel.add(label);



        container.add(panel, BorderLayout.CENTER);
        pack();
        FrameManager.centerWindow(this);
    }

}
