package com.slimtrade.gui.buttons;

import javax.swing.*;
import java.awt.*;

@Deprecated
public class ColorButton extends JButton {

    public ColorButton(String text, Color color) {
        super(text);
        setBackground(color);
    }

}
