package com.slimtrade.gui.pinning;

import java.awt.*;

public interface IPinnable {

    boolean isPinned();

    void unpin();

    void applyPin(Rectangle rect);

    String getPinTitle();

    Rectangle getPinRect();

}
