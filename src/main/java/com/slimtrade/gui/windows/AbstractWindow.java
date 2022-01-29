package com.slimtrade.gui.windows;

import javax.swing.*;
import java.awt.*;

public class AbstractWindow extends JFrame {

    protected Container container;

    private boolean screenLock;
    private Rectangle screenBounds;

    public AbstractWindow(String title) {
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setAlwaysOnTop(true);
        this.container = getContentPane();
    }

}
