package com.slimtrade.gui.custom;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.IColorable;

import javax.swing.*;
import java.awt.*;

public class CustomScrollPane extends JScrollPane implements IColorable {

    public static int DEFAULT_SCROLL_SPEED = 14;
    private JPanel cornerPanel = new JPanel();

    public CustomScrollPane(Component view) {
        super(view);
//        this.setVerticalScrollBar(this.createVerticalScrollBar());
//        this.setHorizontalScrollBar(this.createHorizontalScrollBar());
        this.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        this.getVerticalScrollBar().setUnitIncrement(DEFAULT_SCROLL_SPEED);
        this.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
        this.getHorizontalScrollBar().setUnitIncrement(DEFAULT_SCROLL_SPEED);
        this.setCorner(JScrollPane.LOWER_RIGHT_CORNER, cornerPanel);
    }

    @Override
    public void updateColor() {
        cornerPanel.setBackground(ColorManager.PRIMARY);
    }
}
