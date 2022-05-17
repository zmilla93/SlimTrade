package com.slimtrade.gui.menubar;

import javax.swing.*;
import java.awt.*;

public class MenubarButton extends JButton {

    public MenubarButton(String text) {
        setText(text);
        int insetVertical = 0;
        int insetHorizontal = 10;
        setMargin(new Insets(insetVertical, insetHorizontal, insetVertical, insetHorizontal));
    }
}
