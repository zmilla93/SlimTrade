package com.slimtrade.gui.components.slimtrade;

import com.slimtrade.modules.theme.ThemeManager;

import javax.swing.*;

public class ApproveLabel extends JLabel {

    @Override
    public void updateUI() {
        super.updateUI();
        setForeground(ThemeManager.getCurrentExtensions().approve);
    }

}
