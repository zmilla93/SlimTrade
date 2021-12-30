package com.slimtrade.gui.buttons;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class IconButton extends JButton {

    public IconButton(){
        super();
        Image img = null;
        try {
            img = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/custom/cartx64.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setIcon(new ImageIcon(img.getScaledInstance(18,18,Image.SCALE_SMOOTH)));
//        setPreferredSize(new Dimension(30,30));
    }

    @Override
    public void updateUI() {
        super.updateUI();
//        setPreferredSize(new Dimension(24,24));
        setBorder(null);
    }
}
