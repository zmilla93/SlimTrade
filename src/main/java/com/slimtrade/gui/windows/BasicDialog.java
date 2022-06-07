package com.slimtrade.gui.windows;

import com.slimtrade.core.utility.ColorManager;

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
        ColorManager.addFrame(this);
    }

}
