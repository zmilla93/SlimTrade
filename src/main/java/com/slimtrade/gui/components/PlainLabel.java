package com.slimtrade.gui.components;

import javax.swing.*;
import java.awt.*;

public class PlainLabel extends JLabel {

    public PlainLabel() {
        this(null);
    }

    public PlainLabel(String text) {
        setText(text);
        Font font = getFont();
        setFont(font.deriveFont(Font.PLAIN, font.getSize()));
    }

}
