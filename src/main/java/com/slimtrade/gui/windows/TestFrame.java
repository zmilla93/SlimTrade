package com.slimtrade.gui.windows;

import com.slimtrade.core.utility.ColorManager;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.messaging.NotificationPanel;

import javax.swing.*;
import java.awt.*;

public class TestFrame extends JFrame {

    JPanel contentPanel = new JPanel();

    public TestFrame() {
        setContentPane(contentPanel);
        setFocusable(false);
        setFocusableWindowState(false);
        setAlwaysOnTop(true);

        ColorManager.addFrame(this);
        contentPanel.setLayout(new FlowLayout());
        contentPanel.add(new IconButton("/icons/default/tagx64.png", 30));


        NotificationPanel panel = new NotificationPanel();
        contentPanel.add(panel);


        pack();
        setSize(500, 500);
        setVisible(true);
        panel.resize();

    }


}
