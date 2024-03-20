package com.slimtrade.modules.theme;

import com.slimtrade.core.managers.SaveManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

@Deprecated
public class IconFactory {

    // FIXME: move this to IconButton once that is working
//    public static int defaultSize = 20;
    private static final HashMap<String, ImageIcon> iconMap = new HashMap<>();
    private static final HashMap<String, ImageIcon> colorIconMap = new HashMap<>();
    private static int cacheIconSize = -1;

//    public static ImageIcon getImageIcon(String path) {
//        return getImageIcon(path, -1);
//    }

    public static ImageIcon getIcon(String path) {
        return getIcon(path, cacheIconSize, true);
    }

    public static ImageIcon getIcon(String path, boolean resourceFolder) {
        return getIcon(path, cacheIconSize, resourceFolder);
    }

    public static ImageIcon getIcon(String path, int size) {
        return getIcon(path, size, true);
    }

    /**
     * When getting an icon, size uses cached size, -1 uses unscaled size
     *
     * @param path
     * @param size
     * @param resourceFolder
     * @return
     */
    public static ImageIcon getIcon(String path, int size, boolean resourceFolder) {
        if (cacheIconSize != SaveManager.settingsSaveFile.data.iconSize) {
            clearCache();
        }
        if (size == 0) size = cacheIconSize;
        // Return cached image if possible
        if (size == cacheIconSize && iconMap.containsKey(path)) {
            return iconMap.get(path);
        }
        try {
            BufferedImage img;
            if (resourceFolder) img = ImageIO.read(Objects.requireNonNull(ThemeManager.class.getResource(path)));
            else img = ImageIO.read(new File(path));
            if (img == null) return null; // This will only trigger with user submitted images
            ImageIcon icon;
            if (size == -1) {
                icon = new ImageIcon(img);
            } else {
                icon = new ImageIcon(img.getScaledInstance(size, size, Image.SCALE_SMOOTH));
            }
            if (size == cacheIconSize) iconMap.put(path, icon);
            return icon;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ImageIcon getColorIcon(String path) {
        return getColorIcon(path, cacheIconSize);
    }

    public static ImageIcon getColorIcon(String path, int size) {
        if (cacheIconSize != SaveManager.settingsSaveFile.data.iconSize) {
            clearCache();
        }
        if (size == 0) size = cacheIconSize;
        // Return cached image if possible
        if (size == cacheIconSize && colorIconMap.containsKey(path)) {
            return colorIconMap.get(path);
        }
        // Generate new image
        try {
            BufferedImage img = ImageIO.read(Objects.requireNonNull(ThemeManager.class.getResource(path)));
            ImageIcon icon = new ImageIcon(getColorImage(img, UIManager.getColor("Button.foreground")).getScaledInstance(size, size, Image.SCALE_SMOOTH));
            if (size == cacheIconSize) colorIconMap.put(path, icon);
            return icon;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Replaces the pixels in an image with a new color
     *
     * @param image
     * @param color
     * @return
     */
    private static Image getColorImage(BufferedImage image, Color color) {
        int width = image.getWidth();
        int height = image.getHeight();
        WritableRaster raster = image.getRaster();
        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                int[] pixels = raster.getPixel(xx, yy, (int[]) null);
                pixels[0] = color.getRed();
                pixels[1] = color.getGreen();
                pixels[2] = color.getBlue();
                raster.setPixel(xx, yy, pixels);
            }
        }
        return image;
    }

    private static void clearCache() {
        iconMap.clear();
        colorIconMap.clear();
        cacheIconSize = SaveManager.settingsSaveFile.data.iconSize;
    }

}
