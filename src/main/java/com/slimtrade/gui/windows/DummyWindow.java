package com.slimtrade.gui.windows;


import java.awt.*;

public class DummyWindow extends AbstractDialog {

    public DummyWindow() {
        setSize(50, 50);
        setFocusable(true);
        setFocusableWindowState(true);
        setBackground(new Color(0, 0, 0, 0.005f));
    }
}
