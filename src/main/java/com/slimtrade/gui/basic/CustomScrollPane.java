package com.slimtrade.gui.basic;

import com.slimtrade.gui.basic.CustomScrollBarUI;

import javax.swing.*;
import java.awt.*;

public class CustomScrollPane extends JScrollPane {

    public static int DEFAULT_SCROLL_SPEED = 14;

    public CustomScrollPane(){
        super();
        this.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        this.getVerticalScrollBar().setUnitIncrement(DEFAULT_SCROLL_SPEED);
        this.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
        this.getHorizontalScrollBar().setUnitIncrement(DEFAULT_SCROLL_SPEED);
        this.setPreferredSize(new Dimension(10, 0));
//        this.setPreferredSize(new Dimension(0, 10));
    }

    public CustomScrollPane(JPanel panel){
        super(panel);
//        this.setVerticalScrollBar(this.createVerticalScrollBar());
//        this.setHorizontalScrollBar(this.createHorizontalScrollBar());
        this.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        this.getVerticalScrollBar().setUnitIncrement(DEFAULT_SCROLL_SPEED);
        this.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
        this.getHorizontalScrollBar().setUnitIncrement(DEFAULT_SCROLL_SPEED);
    }

}
