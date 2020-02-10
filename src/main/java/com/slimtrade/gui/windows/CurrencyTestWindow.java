package com.slimtrade.gui.windows;

import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.enums.POEImage;

import javax.swing.*;
import java.awt.*;

public class CurrencyTestWindow extends JFrame {

    public CurrencyTestWindow() {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new FlowLayout());
//        for(CurrencyType t : CurrencyType.values()) {
//            if(t.getPath() != null) {
//                System.out.println("PATH : " + t.getPath());
//                this.add(new IconButton(t.getPath(), 30));
//            }
//        }
        System.out.println("Images Run 1");
        for (POEImage t : POEImage.values()) {
            if(t.isValid()) {
                this.add(new IconButton(t.getImage(30), 30));
            }
        }
//        System.out.println("Images Run 2");
//        for (POEImage t : POEImage.values()) {
//            if(t.isValid()) {
//                this.add(new IconButton(t.getImage(30), 30));
//            }
//        }
//        System.out.println("Images Run 3");
//        for (POEImage t : POEImage.values()) {
//            if(t.isValid()) {
//                this.add(new IconButton(t.getImage(15), 15));
//            }
//        }
        System.out.println("End Images");
        this.pack();
        this.setVisible(true);
        this.setLocation(300, 300);
        this.setAlwaysOnTop(true);
    }

}
