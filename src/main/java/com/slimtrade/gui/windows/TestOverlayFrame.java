package com.slimtrade.gui.windows;

import javax.swing.*;
import java.awt.*;

public class TestOverlayFrame extends JFrame {

    public TestOverlayFrame() {

        setAlwaysOnTop(true);
        setUndecorated(true);
        JPanel panel = new JPanel();
        setBackground(new Color(47, 43, 43));
//        panel.setBackground(new Color(47, 43, 43));
//        setContentPane(panel);
        setFocusable(false);
        setFocusableWindowState(false);
        pack();
        setSize(20, 20);
//        setVisible(true);
    }

}
