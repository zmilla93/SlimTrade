package com.slimtrade.gui.tutorial.panels;

import com.slimtrade.core.managers.ColorManager;

import javax.swing.*;
import java.awt.*;

public class ImageLabel extends JLabel {

    public ImageLabel(String path) {
        Image image = new ImageIcon(this.getClass().getClassLoader().getResource(path)).getImage();
        this.setIcon(new ImageIcon(image));
    }

}
