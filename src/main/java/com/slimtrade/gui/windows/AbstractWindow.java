package com.slimtrade.gui.windows;

import com.slimtrade.core.utility.ColorManager;

import javax.swing.*;
import java.awt.*;

public class AbstractWindow extends JFrame {

    protected Container container;

    public AbstractWindow(String title) {
        setTitle(title);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setAlwaysOnTop(true);
        this.container = getContentPane();
        ColorManager.addFrame(this);
    }

}
