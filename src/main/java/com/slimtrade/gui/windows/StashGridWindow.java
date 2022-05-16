package com.slimtrade.gui.windows;

import com.slimtrade.core.utility.ColorManager;

import javax.swing.*;
import java.awt.*;

public class StashGridWindow extends CustomDialog {

//    private Container container;

    public StashGridWindow() {
        setTitle("Stash Overlay");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setAlwaysOnTop(true);
//        this.container = getContentPane();
        ColorManager.addFrame(this);
        JPanel testPanel = new JPanel();
        testPanel.setPreferredSize(new Dimension(400, 400));
        testPanel.setBackground(Color.RED);

        contentPanel.setBackground(new Color(100,0,0,100));
//        contentPanel.setBackground(Color.GREEN);
//        container.setBackground(new Color(1, 1, 1, 0.5f));
//        setBackground(new Color(1,1,1));
//        setUndecorated(true);
//        setOpacity(0.5f);

//        container.setLayout(new BorderLayout());
//        container.add(Box.createVerticalStrut(20), BorderLayout.NORTH);
//
//        container.add(testPanel, BorderLayout.CENTER);
        pack();
        setSize(200,200);
    }

}
