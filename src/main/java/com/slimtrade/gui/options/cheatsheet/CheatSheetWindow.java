package com.slimtrade.gui.options.cheatsheet;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.gui.basic.AbstractWindow;
import com.slimtrade.gui.custom.CustomLabel;
import com.slimtrade.gui.options.ISaveable;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class CheatSheetWindow extends AbstractWindow implements ISaveable {

    private CheatSheetData data;

    public CheatSheetWindow(CheatSheetData data) {
        super(data.getCleanName(), true, true);
        this.data = data;
        JLabel label = new CustomLabel();
        try {
            File file = new File(data.fileName);
            if(!file.exists()) {
                return;
            }
            Image image = ImageIO.read(file.toURI().toURL());
            label.setIcon(new ImageIcon(image));
            this.add(label);
            this.pack();
            ColorManager.recursiveColor(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void pinAction(MouseEvent e) {
        super.pinAction(e);
//        load();
        System.out.println("PIN : " + pinned);
    }

    @Override
    public void save() {

    }

    @Override
    public void load() {
        if(data.pinElement.pinned) {
            setLocation(data.pinElement.anchor);
//            data.pinElement.anchor

        }
    }
}
