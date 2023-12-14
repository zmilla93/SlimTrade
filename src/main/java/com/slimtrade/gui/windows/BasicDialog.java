package com.slimtrade.gui.windows;

import com.slimtrade.modules.theme.ThemeManager;

import javax.swing.*;

public class BasicDialog extends VisibilityDialog {

    protected final JPanel contentPanel = new JPanel();

    public BasicDialog() {
        setUndecorated(true);
        setContentPane(contentPanel);
        setFocusable(false);
        setFocusableWindowState(false);
        setAlwaysOnTop(true);
        setType(JDialog.Type.UTILITY);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ThemeManager.addFrame(this);
    }

}
