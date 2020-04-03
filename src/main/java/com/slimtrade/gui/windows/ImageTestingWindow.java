package com.slimtrade.gui.windows;

import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.enums.CustomIcons;
import com.slimtrade.gui.enums.DefaultIcons;

import javax.swing.*;
import java.awt.*;

public class ImageTestingWindow extends JFrame {

    private JPanel p1 = new JPanel();
    private JPanel p2 = new JPanel();
    private JPanel p3 = new JPanel();

    public ImageTestingWindow() {

        // Default Images
        for (DefaultIcons i : DefaultIcons.values()) {
            p1.add(new IconButton(i, 20));
        }
        // Custom Images
        for (CustomIcons i : CustomIcons.values()) {
            p2.add(new IconButton(i, 20));
        }
//        // POE Images
//        for (POEImage t : POEImage.values()) {
//            if(t.isValid()) {
//                p3.add(new IconButton(t, 30));
//            }
//        }

        this.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;

        this.add(new Label("Default Images"), gc);
        gc.gridx++;
        this.add(p1, gc);
        gc.gridx = 0;
        gc.gridy++;

        this.add(new Label("Custom Images"), gc);
        gc.gridx++;
        this.add(p2, gc);
        gc.gridx = 0;
        gc.gridy++;

        this.add(new Label("POE Images"), gc);
        gc.gridx++;
        this.add(p3, gc);
        gc.gridx = 0;
        gc.gridy++;

        this.pack();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocation(300, 300);
        this.setAlwaysOnTop(true);
    }

}
