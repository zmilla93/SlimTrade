package com.slimtrade.modules.theme;

import com.formdev.flatlaf.icons.FlatCheckBoxIcon;
import com.slimtrade.core.managers.FontManager;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.buttons.NotificationButton;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.gui.windows.BasicDialog;
import com.slimtrade.modules.theme.components.ColorCheckbox;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.*;

public class ThemeManager {

    private static final List<Component> frames = new ArrayList<>();
    private static Theme currentTheme;
    private static final ArrayList<IThemeListener> themeListeners = new ArrayList<>();
    private static final ArrayList<IUIResizeListener> uiResizeListeners = new ArrayList<>();

    public static Color POE_TEXT_DARK = new Color(53, 28, 13);
    public static Color POE_TEXT_LIGHT = new Color(254, 192, 118);
    private static final int DEFAULT_OFFSET_COLOR_AMOUNT = 20;

    public static final Color TRANSPARENT = new Color(0, 0, 0, 0);
    public static final Color TRANSPARENT_CLICKABLE = new Color(0, 0, 0, 1);

    private static final ArrayList<JComboBox<?>> stickyCombos = new ArrayList<>();

    private static Font font;

    private static final HashMap<String, ImageIcon> iconMap = new HashMap<>();
    private static final HashMap<String, ImageIcon> colorIconMap = new HashMap<>();

    private static int cacheIconSize = 18;

    public static boolean addFrame(Component frame) {
        if (!frames.contains(frame)) {
            return frames.add(frame);
        }
        return true;
    }

    public static boolean removeFrame(Component frame) {
        return frames.remove(frame);
    }

    // Sticky combos are JComboBoxes that contain colored icons.
    // These combos need to have their selected values manually updated after switching themes

    public static void addStickyCombo(JComboBox<?> combo) {
        stickyCombos.add(combo);
    }

    public static void removeStickyCombo(JComboBox<?> combo) {
        stickyCombos.remove(combo);
    }

    public static void setTheme(Theme theme) {
        setTheme(theme, false);
    }

    public static void setTheme(Theme theme, boolean forceThemeRefresh) {
        if (theme == null) theme = Theme.getDefaultColorTheme();
        if (theme == currentTheme && !forceThemeRefresh) return;
        int[] comboIcons = new int[stickyCombos.size()];
        for (int i = 0; i < stickyCombos.size(); i++) {
            comboIcons[i] = stickyCombos.get(i).getSelectedIndex();
        }
        iconMap.clear();
        colorIconMap.clear();
        currentTheme = theme;
        try {
            UIManager.setLookAndFeel(currentTheme.lookAndFeel);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        // Solarized Light Fix - Makes checkboxes color match other inputs instead of being white
        if (currentTheme.name().equals("SOLARIZED_LIGHT")) {
            UIManager.put("CheckBox.icon", new ColorCheckbox());
        } else {
            UIManager.put("CheckBox.icon", new FlatCheckBoxIcon());
        }
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

    public static Theme getCurrentTheme() {
        if (currentTheme == null) return Theme.getDefaultColorTheme();
        return currentTheme;
    }

    public static void loadFonts() {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(FontManager.class.getResourceAsStream("/font/Roboto-Regular.ttf")));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            boolean success = ge.registerFont(font);
//            System.out.println("Font loaded: " + success);
//            System.out.println("New Font:" + font.getName());
//            System.out.println("New Font:" + font.getFamily());
//            for (String f : ge.getAvailableFontFamilyNames()) {
//                System.out.println("Font ::: " + f);
//            }
//            font = new Font("Mongolian Baiti", Font.PLAIN, 2);
            font = new Font("Arial", Font.PLAIN, 2);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void refreshDefaultFonts() {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof Font) {
                Font oldFont = (Font) value;
                Font newFont = Font.getFont("Roboto-Regular");
                UIManager.put(key, font.deriveFont(oldFont.getStyle(), SaveManager.settingsSaveFile.data.fontSize));
            }
        }
    }


    // When getting an icon, size uses cached size, -1 uses unscaled size
    public static ImageIcon getIcon(String path) {
        return getIcon(path, cacheIconSize, true);
    }

    public static ImageIcon getIcon(String path, boolean resourceFolder) {
        return getIcon(path, cacheIconSize, resourceFolder);
    }

    public static ImageIcon getIcon(String path, int size) {
        return getIcon(path, size, true);
    }

    public static ImageIcon getIcon(String path, int size, boolean resourceFolder) {
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

    public static void updateFont(Frame frame) {
        for (Component c : frame.getComponents()) {
//            c.setFont(c.getFont());
        }
    }

//    private static void setUIFont(Font font) {
//        Enumeration<Object> keys = UIManager.getDefaults().keys();
//        while (keys.hasMoreElements()) {
//            Object key = keys.nextElement();
//            Object value = UIManager.get(key);
//            if (value instanceof Font)
//                UIManager.put(key, font);
//        }
//    }

    public static void setFontSize(int size) {
//        if(true) return;
        refreshDefaultFonts();
        for (Component frame : frames) {
            setFontSizeRecursive(frame, size);
            SwingUtilities.updateComponentTreeUI(frame);
            frame.revalidate();
            frame.repaint();
        }
        for (IUIResizeListener listener : uiResizeListeners) {
            listener.onFontSizeChanged();
        }
        // FIXME : add an interface for this
        if (FrameManager.messageManager != null) FrameManager.messageManager.pack();
    }

    public static int getCachedIconSize() {
        return cacheIconSize;
    }

    public static void setIconSize(int size) {
        assert (SwingUtilities.isEventDispatchThread());
        cacheIconSize = size;
        iconMap.clear();
        colorIconMap.clear();
        for (Component frame : frames) {
            setIconSizeRecursive(frame, size);
            SwingUtilities.updateComponentTreeUI(frame);
            frame.revalidate();
            frame.repaint();
            // FIXME
            if (frame instanceof BasicDialog) {
                ((BasicDialog) frame).pack();
            }
        }
        for (IUIResizeListener listener : uiResizeListeners) {
            listener.onIconSizeChanged();
        }
    }

    private static void setFontSizeRecursive(Component component, int size) {
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                setFontSizeRecursive(child, size);
            }
        }
        Font curFont = component.getFont();
        component.setFont(font.deriveFont(curFont.getStyle(), size));
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

//    public static JLabel applyLanguageFont(JLabel component){
//        FontManager.getFontLanguage(component.getText());
//        FontManager.applyFont(component);
//        return component;
//    }

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

    //
    // Utility Color Functions
    //

    public static Color getDarkerColor(Color colorA, Color colorB) {
        if (colorIntValue(colorA) < colorIntValue(colorB)) return colorA;
        return colorB;
    }

    public static Color getLighterColor(Color colorA, Color colorB) {
        if (colorIntValue(colorA) > colorIntValue(colorB)) return colorA;
        return colorB;
    }

    public static Color modify(Color c, int mod) {
        int min = 0;
        int max = 255;
        int r = ZUtil.clamp(c.getRed() + mod, min, max);
        int g = ZUtil.clamp(c.getGreen() + mod, min, max);
        int b = ZUtil.clamp(c.getBlue() + mod, min, max);
        return new Color(r, g, b);
    }

    public static Color lighter(Color c) {
        return modify(c, DEFAULT_OFFSET_COLOR_AMOUNT);
    }

    public static Color lighter(Color c, int increase) {
        return modify(c, increase);
    }

    private static int colorIntValue(Color color) {
        return color.getRed() + color.getGreen() + color.getBlue();
    }

    public static Color adjustAlpha(Color color, int alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }

    /**
     * Dumps all the Key, Value pairs from the UIManager into console. ~1300 entries.
     */
    public static void debugKeyValueDump() {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        StringBuilder builder = new StringBuilder();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            builder.append(key).append(" :::").append(value).append("\n");
        }
        StringSelection selection = new StringSelection(builder.toString());
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
    }

    //
    // Listeners
    //

    public static void addThemeListener(IThemeListener listener) {
        themeListeners.add(listener);
    }

    public static void removeThemeListener(IThemeListener listener) {
        themeListeners.remove(listener);
    }

    public static void clearAllThemeListeners() {
        themeListeners.clear();
    }

    public static void addFontListener(IUIResizeListener listener) {
        uiResizeListeners.add(listener);
    }

    public static void removeFontListener(IUIResizeListener listener) {
        uiResizeListeners.remove(listener);
    }

    public static void clearAllFontListeners() {
        uiResizeListeners.clear();
    }

}
