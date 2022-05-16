package com.slimtrade.core.utility;

import com.slimtrade.modules.colortheme.IThemeListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.util.List;
import java.util.*;

public class ColorManager<T> {

    private static List<Component> frames = new ArrayList<>();
    private static ColorTheme currentTheme;
    private static ArrayList<IThemeListener> themeListeners = new ArrayList<>();

    //    public static final Color CONFIRM_COLOR = new Color(58, 150, 47, 255);
    public static final Color CONFIRM_COLOR = new Color(73, 156, 84);
    //    public static final Color DENY_COLOR = new Color(154, 75, 75, 255);
    public static final Color DENY_COLOR = new Color(199, 84, 80);
    public static final Color GREEN_SALE = new Color(0, 130, 0);
    public static final Color RED_SALE = new Color(130, 0, 0);

    public static final Color TRANSPARENT = new Color(0, 0, 0, 0);
    public static final Color TRANSPARENT_CLICKABLE = new Color(0, 0, 0, 1);

    private static ArrayList<JComboBox> stickyCombos = new ArrayList<>();


    private static HashMap<String, ImageIcon> iconMap = new HashMap<>();

    public static int iconSize = 18;

    public static boolean addFrame(Component frame) {
        if (!frames.contains(frame)) {
            return frames.add(frame);
        }
        return true;
    }

    public static boolean removeFrame(JFrame frame) {
        return frames.remove(frame);
    }

    // Sticky combos are JComboBoxes that contain colored icons.
    // These combos need to be manually updated when switching themes.
    public static void addStickyCombo(JComboBox combo) {
        stickyCombos.add(combo);
    }

    public static void removeStickyCombo(JComboBox combo) {
        stickyCombos.remove(combo);
    }

    public static void setTheme(ColorTheme theme) {
        setTheme(theme, false);
    }

    public static void setTheme(ColorTheme theme, boolean forceRefresh) {
        int[] comboIcons = new int[stickyCombos.size()];
        for (int i = 0; i < stickyCombos.size(); i++) {
            comboIcons[i] = stickyCombos.get(i).getSelectedIndex();
        }
        iconMap.clear();
        if (theme == currentTheme && !forceRefresh) return;
        currentTheme = theme;
        try {
            UIManager.setLookAndFeel(currentTheme.lookAndFeel);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
        ColorManager.setUIFont(font);
        for (Component frame : frames) {
            JRootPane rootPane = null;
            if (frame instanceof RootPaneContainer) rootPane = ((RootPaneContainer) frame).getRootPane();
            if (rootPane != null) {
                SwingUtilities.updateComponentTreeUI(rootPane);
                frame.revalidate();
            }
        }
        for (int i = 0; i < stickyCombos.size(); i++) {
            stickyCombos.get(i).setSelectedIndex(comboIcons[i]);
        }
        for (IThemeListener listener : themeListeners) {
            listener.onThemeChange();
        }
    }

    public static ImageIcon getIcon(String path) {
        // Return cached image if possible
        if (iconMap.containsKey(path)) {
            return iconMap.get(path);
        }
        // Generate new image
        try {
            BufferedImage img = ImageIO.read(Objects.requireNonNull(ColorManager.class.getResource(path)));
            ImageIcon icon = new ImageIcon(getColorImage(img, UIManager.getColor("Button.foreground")).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
            iconMap.put(path, icon);
            return icon;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

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

    private static void setUIFont(Font f) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
//        f=f.deriveFont(f.getStyle(), 24);
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof Font)
                UIManager.put(key, f);
        }
    }

    public static Color getDarkerColor(Color colorA, Color colorB) {
        if (colorIntValue(colorA) < colorIntValue(colorB)) return colorA;
        return colorB;
    }

    public static Color getLighterColor(Color colorA, Color colorB) {
        if (colorIntValue(colorA) > colorIntValue(colorB)) return colorA;
        return colorB;
    }

    private static int colorIntValue(Color color) {
        return color.getRed() + color.getGreen() + color.getBlue();
    }

    public static Color adjustAlpha(Color color, int alpha){
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }

    public static void addListener(IThemeListener listener) {
        themeListeners.add(listener);
    }

    public static void removeListener(IThemeListener listener) {
        themeListeners.remove(listener);
    }

    public static void clearAllListeners() {
        themeListeners.clear();
    }

}
