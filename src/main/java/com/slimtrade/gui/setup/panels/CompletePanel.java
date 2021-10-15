package com.slimtrade.gui.setup.panels;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.IColorable;
import com.slimtrade.gui.custom.CustomLabel;

import javax.swing.*;

public class CompletePanel extends AbstractSetupPanel implements IColorable {

    private JLabel info1 = new CustomLabel("SlimTrade setup complete!");

    public CompletePanel() {
        assert(SwingUtilities.isEventDispatchThread());
        this.add(info1, gc);
        gc.gridy++;
        this.add(Box.createHorizontalStrut(400), gc);
    }

    @Override
    public void updateColor() {
        this.setBackground(ColorManager.LOW_CONTRAST_1);
    }
}
