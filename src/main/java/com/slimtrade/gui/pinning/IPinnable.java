package com.slimtrade.gui.pinning;

import java.awt.*;

public interface IPinnable {

    boolean isPinned();

    void applyPin(Rectangle rectangle);

    String getPinTitle();

    Rectangle getPinRectangle();

}
