package com.slimtrade.gui.pinning;

import org.w3c.dom.css.Rect;

import java.awt.*;

public class PinData {

    public final String title;
//    public final Point location;
    public final Rectangle rectangle;

    public PinData(String title, Rectangle rectangle) {
        this.title = title;
        this.rectangle = rectangle;
    }

}
