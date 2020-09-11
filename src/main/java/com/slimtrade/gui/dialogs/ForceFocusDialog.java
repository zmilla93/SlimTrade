package com.slimtrade.gui.dialogs;

import javax.swing.*;
import java.awt.*;

public class ForceFocusDialog extends JDialog {

    public ForceFocusDialog() {
        setSize(5, 5);
        setUndecorated(true);
        setOpacity(0.005f);
    }

    public void moveToMouse() {
        Point p = MouseInfo.getPointerInfo().getLocation();
        setLocation(p.x - 2, p.y - 2);
    }

}
