package com.slimtrade.gui.buttons;

import javax.swing.*;
import java.awt.*;

public class SlimButton extends JButton {

    public SlimButton(String text){
        super(text);
        setBorder(null);
        setBorder(BorderFactory.createEmptyBorder(0,4,0,4));
        setPreferredSize(new Dimension(getPreferredSize().width, 24));
    }

}
