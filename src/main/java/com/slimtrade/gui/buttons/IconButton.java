package com.slimtrade.gui.buttons;

import com.slimtrade.core.utility.ColorManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class IconButton extends JButton {

    public IconButton(String path){
        super();
        setIcon(ColorManager.getIcon(path));
//        setPreferredSize(new Dimension(30,30));
    }

    @Override
    public void updateUI() {
        super.updateUI();
//        setPreferredSize(new Dimension(24,24));
        setBorder(null);
    }
}
