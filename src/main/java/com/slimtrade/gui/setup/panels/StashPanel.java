package com.slimtrade.gui.setup.panels;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.custom.CustomLabel;
import com.slimtrade.gui.buttons.BasicButton;
import com.slimtrade.gui.enums.WindowState;

import javax.swing.*;

public class StashPanel extends AbstractSetupPanel implements ISetupValidator, IColorable {

    JLabel info1 = new CustomLabel("Resize the overlay until it aligns with your stash in game.");
    JLabel info2 = new CustomLabel("Path of Exile should be open for this step.");

    JButton editButton = new BasicButton("Edit Stash Overlay");

    JPanel innerPanel = new JPanel(FrameManager.gridBag);

    public StashPanel() {

        container.add(info1, gc);
        gc.gridy++;
        gc.insets.bottom = 15;
        container.add(info2, gc);
        gc.gridy++;

        gc.insets.bottom = 0;
        container.add(editButton, gc);
        gc.gridy = 0;

        editButton.addActionListener(e -> {
            FrameManager.setupWindow.setVisible(false);
            FrameManager.lastWindowState = WindowState.SETUP;
            FrameManager.windowState = WindowState.STASH_OVERLAY;
            FrameManager.stashOverlayWindow.setShow(true);
            FrameManager.stashOverlayWindow.setAlwaysOnTop(false);
            FrameManager.stashOverlayWindow.setAlwaysOnTop(true);
            FrameManager.stashOverlayWindow.repaint();
        });

    }

    @Override
    public boolean isValidInput() {
        return App.saveManager.stashSaveFile.initialized;
    }

    @Override
    public void save() {
        // Saving is already handled elsewhere
    }

    @Override
    public void updateColor() {
        this.setBackground(ColorManager.LOW_CONTRAST_1);
        container.setBackground(ColorManager.LOW_CONTRAST_1);
    }
}
