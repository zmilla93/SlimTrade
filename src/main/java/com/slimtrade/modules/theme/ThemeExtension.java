package com.slimtrade.modules.theme;

import javax.swing.*;
import java.awt.*;

public class ThemeExtension {

    public Color approve = Color.GREEN;
    public Color indeterminate = Color.ORANGE;
    //    public Color indeterminate = Color.ORANGE;
    public Color deny = Color.RED;
    private Color textColor;

    public Color incoming = Color.GREEN;
    public Color outgoing = Color.RED;
    public Color scanner = Color.ORANGE;
    public Color update = Color.CYAN;

    public ThemeExtension() {

    }

    public Color getTextColor() {
        if (textColor == null) textColor = UIManager.getColor("Label.foreground");
        return textColor;
    }

}
