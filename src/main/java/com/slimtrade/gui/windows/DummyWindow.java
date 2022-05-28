package com.slimtrade.gui.windows;


import com.slimtrade.core.utility.ColorManager;

import javax.swing.*;

public class DummyWindow extends JDialog {

    public static final int SIZE = 50;
    public static final int HALF_SIZE = SIZE / 2;

    public DummyWindow() {
        setSize(SIZE, SIZE);
        setUndecorated(true);
        setAlwaysOnTop(true);
        setBackground(ColorManager.TRANSPARENT_CLICKABLE);
    }
}
