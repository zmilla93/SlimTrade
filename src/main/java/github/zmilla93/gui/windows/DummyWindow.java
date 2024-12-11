package github.zmilla93.gui.windows;


import github.zmilla93.modules.theme.ThemeManager;

import javax.swing.*;

public class DummyWindow extends JDialog {

    public static final int SIZE = 50;
    public static final int HALF_SIZE = SIZE / 2;

    public DummyWindow() {
        setSize(SIZE, SIZE);
        setUndecorated(true);
        setAlwaysOnTop(true);
        setBackground(ThemeManager.TRANSPARENT_CLICKABLE);
    }
}
