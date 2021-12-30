package com.slimtrade.gui.windows;

import javax.swing.*;
import java.awt.*;

public class AbstractWindow extends JFrame {

    protected Container container;

    public AbstractWindow(String title){
        super(title);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setAlwaysOnTop(true);
        this.container = getContentPane();
    }

}
