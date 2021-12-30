package com.slimtrade.core.utility;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.util.List;
import java.util.*;

public class ColorManager {

    private static List<JFrame> frames = new ArrayList<>();
    private static ColorTheme currentTheme;

    //    public static final Color CONFIRM_COLOR = new Color(58, 150, 47, 255);
    public static final Color CONFIRM_COLOR = new Color(73, 156, 84);
    //    public static final Color DENY_COLOR = new Color(154, 75, 75, 255);
    public static final Color DENY_COLOR = new Color(199, 84, 80);
    public static final Color GREEN_SALE = new Color(0, 130, 0);
    public static final Color RED_SALE = new Color(130, 0, 0);


    private static HashMap<String, ImageIcon> iconMap = new HashMap<>();

    public static int iconSize = 18;

    public static boolean addFrame(JFrame frame) {
        if (!frames.contains(frame)) {
            return frames.add(frame);
        }
        return true;
    }

    public static boolean removeFrame(JFrame frame) {
        return frames.remove(frame);

    }

    public static void setTheme(ColorTheme theme) {
        setTheme(theme, false);
    }

    public static void setTheme(ColorTheme theme, boolean forceRefresh) {
        iconMap.clear();
        if (theme == currentTheme && !forceRefresh) return;
        currentTheme = theme;
        try {
            UIManager.setLookAndFeel(currentTheme.lookAndFeel);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
        setUIFont(font);
        for (JFrame frame : frames) {
            SwingUtilities.updateComponentTreeUI(frame.getRootPane());
            frame.revalidate();
        }
    }

    public static ImageIcon getIcon(String path) {
        // Return cached image if possible
        if (iconMap.containsKey(path)) {
            return iconMap.get(path);
        }
        // Generate new image
        try {
            System.out.println("Generating image... " + path);
            BufferedImage img = ImageIO.read(Objects.requireNonNull(ColorManager.class.getResource(path)));
            ImageIcon icon = new ImageIcon(getColorImage(img, UIManager.getColor("Button.foreground")).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
            iconMap.put(path, icon);
            return icon;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public Image getImage(String path) {
//        Image image;
//        if (image == null) {
//            try {
//                image = ImageIO.read(Objects.requireNonNull(getClass().getResource(path)));
//                return image;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }

    public static Image getColorImage(BufferedImage image, Color color) {
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

    public static void setUIFont(Font f) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof Font)
                UIManager.put(key, f);
        }
    }

}
