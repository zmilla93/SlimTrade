package com.slimtrade.core.utility;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.buttons.NotificationButton;
import com.slimtrade.gui.windows.BasicDialog;
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
    public static Color POE_TEXT_DARK = new Color(53, 28, 13);
    public static Color POE_TEXT_LIGHT = new Color(254, 192, 118);

    public static final Color TRANSPARENT = new Color(0, 0, 0, 0);
    public static final Color TRANSPARENT_CLICKABLE = new Color(0, 0, 0, 1);

    private static ArrayList<JComboBox> stickyCombos = new ArrayList<>();

    private static Font font = new Font(Font.SANS_SERIF, Font.PLAIN, SaveManager.settingsSaveFile.data.textSize);
    ;


    private static HashMap<String, ImageIcon> iconMap = new HashMap<>();

    private static int cacheIconSize = 18;

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
//        if(true){
//            return;
//        }
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
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, SaveManager.settingsSaveFile.data.textSize);
        ColorManager.setUIFont(font);
        for (Component frame : frames) {
            JRootPane rootPane = null;
            if (frame instanceof RootPaneContainer) rootPane = ((RootPaneContainer) frame).getRootPane();
            if (rootPane != null) {
                SwingUtilities.updateComponentTreeUI(rootPane);
                frame.revalidate();
                frame.repaint();
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
        return getIcon(path, cacheIconSize);
    }

    public static ImageIcon getIcon(String path, int size) {
        if (size == 0) size = cacheIconSize;
        // Return cached image if possible
        if (size == cacheIconSize && iconMap.containsKey(path)) {
            return iconMap.get(path);
        }
        // Generate new image
        try {
            BufferedImage img = ImageIO.read(Objects.requireNonNull(ColorManager.class.getResource(path)));
            ImageIcon icon = new ImageIcon(getColorImage(img, UIManager.getColor("Button.foreground")).getScaledInstance(size, size, Image.SCALE_SMOOTH));
            if (size == cacheIconSize) iconMap.put(path, icon);
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

    private static void setUIFont(Font font) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof Font)
                UIManager.put(key, font);
        }
    }

    public static void setFontSize(int size) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            Font newFont = font.deriveFont(font.getStyle(), size);
            if (value instanceof Font)
                UIManager.put(key, newFont);
        }
        for (Component frame : frames) {
            setFontSizeRecursive(frame, size);
            SwingUtilities.updateComponentTreeUI(frame);
            frame.revalidate();
            frame.repaint();
        }
    }

    public static int getCachedIconSize() {
        return cacheIconSize;
    }

    public static void setIconSize(int size) {
        cacheIconSize = size;
        iconMap.clear();
        for (Component frame : frames) {
            setIconSizeRecursive(frame, size);
            frame.revalidate();
            frame.repaint();
            // FIXME
            if (frame instanceof BasicDialog) {
                ((BasicDialog) frame).pack();
            }
        }
    }

    private static void setFontSizeRecursive(Component component, int size) {
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                setFontSizeRecursive(child, size);
            }
        }
        Font font = component.getFont();
        component.setFont(font.deriveFont(font.getStyle(), size));
//        if (component instanceof JComponent) {
//            ((JComponent) component).updateUI();
//        }
    }

    private static void setIconSizeRecursive(Component component, int size) {
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                setIconSizeRecursive(child, size);
            }
        }
        // FIXME :
        if (component instanceof IconButton) {
            ((IconButton) component).setIconSize(size);

        }
        if (component instanceof NotificationButton) {
            ((NotificationButton) component).updateUI();
        }
//        if (component instanceof JComponent) {
//            ((JComponent) component).updateUI();
//        }
    }

    public static void recursiveUpdateUI(JComponent component) {
        component.updateUI();
        for (Component child : component.getComponents()) {
            recursiveUpdateUI((JComponent) child);
        }
    }

//    private static void recursiveUpdateUI(){
//        for (Component frame : frames) {
//            setIconSizeRecursive(frame, size);
//            frame.revalidate();
//            frame.repaint();
//            // FIXME
//            if (frame instanceof BasicDialog) {
//                ((BasicDialog) frame).pack();
//            }
//        }
//    }

    // Utility Color Functions

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

    public static Color adjustAlpha(Color color, int alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }

    // Listeners

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
