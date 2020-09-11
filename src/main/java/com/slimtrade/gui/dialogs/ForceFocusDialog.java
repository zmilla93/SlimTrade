package com.slimtrade.gui.dialogs;

import javax.swing.*;
import java.awt.*;

public class ForceFocusDialog extends JDialog {

    private final int SIZE = 20;
    private final int HALF_SIZE = SIZE / 2;

    public ForceFocusDialog() {
        setSize(SIZE, SIZE);
        setUndecorated(true);
        setOpacity(0.005f);
    }

    public void moveToMouse() {
        Point p = MouseInfo.getPointerInfo().getLocation();
        setLocation(p.x - HALF_SIZE, p.y - HALF_SIZE);
    }

}
