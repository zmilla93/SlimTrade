package com.slimtrade.gui.windows;

import com.slimtrade.core.utility.ColorManager;

import javax.swing.*;
import java.awt.*;

public class BasicDialog extends JDialog {

    private Container contentPane;
    public final JPanel container = new JPanel();

    public BasicDialog() {
        contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(container, BorderLayout.CENTER);
        setUndecorated(true);
        setFocusable(false);
        setFocusableWindowState(false);
        setAlwaysOnTop(true);
        setContentPane(container);
        ColorManager.addFrame(this);
    }

}
