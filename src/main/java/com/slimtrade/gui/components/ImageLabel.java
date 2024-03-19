package com.slimtrade.gui.components;

import javax.swing.*;
import java.util.Objects;

/**
 * Used to easily display an image from the resource folder.
 */
public class ImageLabel extends JLabel {

    private final int BORDER_SIZE;

    public ImageLabel(String path) {
        this(path, 0);
    }

    public ImageLabel(String path, int borderSize) {
        setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(path))));
        this.BORDER_SIZE = borderSize;
        updateUI();
    }

    @Override
    public void updateUI() {
        super.updateUI();
        if (BORDER_SIZE <= 0) return;
        setBorder(BorderFactory.createLineBorder(UIManager.getColor("Separator.foreground"), BORDER_SIZE));
    }

}
