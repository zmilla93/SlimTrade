package com.slimtrade.gui.overlays;

import com.slimtrade.core.enums.AppState;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.gui.windows.AbstractDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OverlayInfoDialog extends AbstractDialog {

    public OverlayInfoDialog() {
        super();

        // Labels
        JLabel info1 = new JLabel("Click and drag the example message.");
        JLabel info2 = new JLabel("Hold SHIFT to lock the message to the current monitor.");

        // Buttons
        JButton cancelButton = new JButton("Cancel");
        JButton restoreDefaultButton = new JButton("Restore Default");
        JButton saveButton = new JButton("Save");

        // Panels
        JPanel infoPanel = new JPanel(new GridBagLayout());
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        JPanel outerPanel = new JPanel(new GridBagLayout());

        // Build

        // Info Panel
        GridBagConstraints gc = ZUtil.getGC();
        infoPanel.add(info1, gc);
        gc.gridy++;
        infoPanel.add(info2, gc);

        // Button Panel
        gc = ZUtil.getGC();
        buttonPanel.add(cancelButton, gc);
        gc.gridx++;
        gc.insets.left = 20;
        buttonPanel.add(restoreDefaultButton, gc);
        gc.gridx++;
        buttonPanel.add(saveButton, gc);

        // Outer Panel
        gc = ZUtil.getGC();
        outerPanel.add(infoPanel, gc);
        gc.gridy++;
        gc.insets.top = 10;
        outerPanel.add(buttonPanel, gc);

        contentPanel.setLayout(new GridBagLayout());
        gc = ZUtil.getGC();
        int inset = 20;
        gc.insets = new Insets(inset, inset, inset, inset);
        contentPanel.add(outerPanel);
//        JPanel panel = new JPanel();
//        panel.setBorder(BorderFactory.createLineBorder(Color.red, 4));
//        panel.add(label);

//        container.add(panel, BorderLayout.CENTER);
//        container.add(cancelButton, BorderLayout.SOUTH);
        pack();
        FrameManager.centerWindow(this);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrameManager.setWindowVisibility(AppState.RUNNING);
                FrameManager.messageOverlay.setLocation(FrameManager.messageManager.getLocation());
            }
        });

        restoreDefaultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrameManager.messageOverlay.setLocation(0, 0);
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Point targetPos = FrameManager.messageOverlay.getLocation();
                // FIXME:
//                App.saveManager.overlaySaveFile.messageLocation = targetPos;
                FrameManager.messageManager.setLocation(targetPos);
                FrameManager.setWindowVisibility(AppState.RUNNING);
            }
        });

    }

}
