package com.slimtrade.gui.windows;

import com.slimtrade.core.utility.ColorManager;
import com.slimtrade.gui.buttons.IconButton;

import javax.swing.*;
import java.awt.*;

public class TestFrame extends JFrame {

    JPanel contentPanel = new JPanel();

    public TestFrame() {
        setContentPane(contentPanel);

        ColorManager.addFrame(this);
        contentPanel.setLayout(new FlowLayout());
        contentPanel.add(new IconButton("/icons/default/tagx64.png", 30));

        pack();
        setSize(500, 500);
        setVisible(true);
    }


}
