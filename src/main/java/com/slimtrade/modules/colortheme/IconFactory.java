package com.slimtrade.modules.colortheme;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class IconFactory {

    // FIXME: move this to IconButton once that is working
    public static int defaultSize = 20;

    public static ImageIcon getImageIcon(String path) {
        return getImageIcon(path, -1);
    }

    public static ImageIcon getImageIcon(String path, int size) {
        boolean noResize = false;
        path = path.startsWith("/") ? path : "/" + path;
//        if (size == -1) noResize = true;
        if (size == -1) size = defaultSize;
        if (size < 1) size = 1;
        try {
            Image image = ImageIO.read(Objects.requireNonNull(IconFactory.class.getResource(path)));
            if (noResize) return new ImageIcon(image);
            else return new ImageIcon(image.getScaledInstance(size, size, Image.SCALE_SMOOTH));
        } catch (IOException | NullPointerException e) {
            System.err.println("[IconFactory] File not file: resources" + path);
            System.err.println("[IconFactory] If this file does exist, try rebuilding the project.");
        }
        return null;
    }

}
