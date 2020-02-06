package com.slimtrade.gui.components;

import com.slimtrade.gui.basic.CustomScrollBarUI;

import javax.swing.*;

public class CustomScrollPane extends JScrollPane {

    public static int DEFAULT_SCROLL_SPEED = 14;

    public CustomScrollPane(){
        super();
        this.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        this.getVerticalScrollBar().setUnitIncrement(DEFAULT_SCROLL_SPEED);
        this.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
        this.getHorizontalScrollBar().setUnitIncrement(DEFAULT_SCROLL_SPEED);
    }

    public CustomScrollPane(JPanel panel){
        super(panel);
        this.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        this.getVerticalScrollBar().setUnitIncrement(DEFAULT_SCROLL_SPEED);
        this.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
        this.getHorizontalScrollBar().setUnitIncrement(DEFAULT_SCROLL_SPEED);
    }

}
