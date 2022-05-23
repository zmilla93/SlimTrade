package com.slimtrade.gui.pinning;

import java.awt.*;

public interface IPinnable {

    boolean isPinned();

    void applyPin(Point point);

    String getPinTitle();

    Point getPinLocation();

}
