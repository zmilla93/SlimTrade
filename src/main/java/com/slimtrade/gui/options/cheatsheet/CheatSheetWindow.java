package com.slimtrade.gui.options.cheatsheet;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.saving.elements.PinElement;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.AbstractWindow;
import com.slimtrade.gui.options.ISaveable;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class CheatSheetWindow extends AbstractWindow implements ISaveable {

    public CheatSheetData data;
    private boolean valid = false;

    public CheatSheetWindow(CheatSheetData data) {
        super(data.getCleanName(), true, true);
        this.data = data;
        JLabel label = new JLabel();
        try {
            File file = new File(data.fileName);
            if (!file.exists()) {
                return;
            }
            Image image = ImageIO.read(file.toURI().toURL());
            if(image == null) return;
            label.setIcon(new ImageIcon(image));
            center.add(label);
            pack();
            ColorManager.recursiveColor(this);
            valid = true;
        } catch (IOException | NullPointerException e) {
            System.out.println("Error while loading cheat sheet : " + data.fileName);
            e.printStackTrace();
        }
    }

    public boolean isValid() {
        return valid;
    }

    @Override
    public void pinAction(MouseEvent e) {
        super.pinAction(e);
        save();
    }

    @Override
    public void save() {
        App.saveManager.pinSaveFile.cheatSheetPins.clear();
        for (CheatSheetWindow w : FrameManager.cheatSheetWindows) {
            PinElement pin = w.getPinElement();
            pin.name = w.data.fileName;
            App.saveManager.pinSaveFile.cheatSheetPins.add(pin);
        }
        App.saveManager.savePinsToDisk();
    }

    @Override
    public void load() {
        if (data.pinElement.pinned) {
            setLocation(data.pinElement.anchor);
        }
    }
}
