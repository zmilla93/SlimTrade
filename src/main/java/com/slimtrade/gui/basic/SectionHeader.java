package com.slimtrade.gui.basic;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;

import javax.swing.*;
import java.awt.*;

public class SectionHeader extends JPanel implements IColorable {

    private final int WIDTH = 300;
    private final int HEIGHT = 22;

    public SectionHeader(String title) {
        App.eventManager.addColorListener(this);
        this.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
//        gc.ipady = 0;
//        gc.fill = GridBagConstraints.BOTH;
        JLabel label = new JLabel(title);
        this.add(label, gc);
        gc.gridx++;
        this.add(Box.createVerticalStrut(HEIGHT), gc);
        gc.gridx = 0;
        gc.gridy++;
        this.add(Box.createHorizontalStrut(WIDTH), gc);

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.updateColor();

    }

    @Override
    public void updateColor() {
        this.setBackground(ColorManager.PRIMARY);
        this.setBorder(BorderFactory.createLineBorder(ColorManager.HIGH_CONTRAST_1));
    }
}
