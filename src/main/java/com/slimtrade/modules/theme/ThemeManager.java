package com.slimtrade.modules.theme;

import com.formdev.flatlaf.icons.FlatCheckBoxIcon;
import com.slimtrade.core.managers.FontManager;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.windows.BasicDialog;
import com.slimtrade.modules.stopwatch.Stopwatch;
import com.slimtrade.modules.theme.components.ColorCheckbox;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.IconUIResource;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.*;

public class ThemeManager {

    // FIXME : Should this be moved to frame manager?
    private static final List<Component> frames = new ArrayList<>();

    // Listeners
    private static final ArrayList<IThemeListener> themeListeners = new ArrayList<>();
    private static final ArrayList<JComboBox<?>> stickyCombos = new ArrayList<>();
    private static final ArrayList<IFontChangeListener> fontChangeListeners = new ArrayList<>();
    private static final ArrayList<IDetailedFontChangeListener> detailedFontChangeListeners = new ArrayList<>();

    private static int cachedIconSize = 0;
    private static int currentFontSize;
    private static final HashMap<String, ImageIcon> iconMap = new HashMap<>();
    private static final HashMap<String, ImageIcon> colorIconMap = new HashMap<>();

    public static final Color TRANSPARENT = new Color(0, 0, 0, 0);
    public static final Color TRANSPARENT_CLICKABLE = new Color(0, 0, 0, 1);
    public static final Color POE_TEXT_DARK = new Color(53, 28, 13);
    public static final Color POE_TEXT_LIGHT = new Color(254, 192, 118);
    private static final int DEFAULT_OFFSET_COLOR_AMOUNT = 20;

    private static Theme currentTheme;
    private static String currentFontName;

    // Flags that monitor changes to font
    private static boolean iconSizeWasChanged = false;
    private static boolean fontSizeWasChanged = false;
    private static boolean fontStyleWasChanged = false;

    public static void addFrame(Component frame) {
        if (frames.contains(frame)) return;
        frames.add(frame);
    }

    public static void removeFrame(Component frame) {
        frames.remove(frame);
    }

    public static List<Component> getFrames() {
        return frames;
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
        assert (SwingUtilities.isEventDispatchThread());
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
        patchTheme();
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

    // Apply custom patching for theme issues. This is a bit hacky, but should be good enough until the themes are updated.
    private static void patchTheme() {
        // If the text areas and panels have the same background, add a border to the text area.
        Color panelBackground = UIManager.getColor("Panel.background");
        Color textAreaBackground = UIManager.getColor("TextArea.background");
        if (panelBackground.equals(textAreaBackground)) {
            Color buttonBorderColor = UIManager.getColor("Button.borderColor");
            UIManager.put("TextArea.border", new BorderUIResource(BorderFactory.createLineBorder(buttonBorderColor)));
        } else {
            UIManager.put("TextArea.border", new BorderUIResource(BorderFactory.createEmptyBorder()));
        }

        // Solarized Light Fix - Make checkboxes color match other inputs instead of being white
        if (currentTheme.name().equals("SOLARIZED_LIGHT")) {
            UIManager.put("CheckBox.icon", new IconUIResource(new ColorCheckbox()));
        } else {
            UIManager.put("CheckBox.icon", new IconUIResource(new FlatCheckBoxIcon()));
        }
    }

    // When getting an icon, size uses cached size, -1 uses unscaled size
    public static ImageIcon getIcon(String path) {
        return getIcon(path, cachedIconSize, true);
    }

    public static ImageIcon getIcon(String path, boolean resourceFolder) {
        return getIcon(path, cachedIconSize, resourceFolder);
    }

    public static ImageIcon getIcon(String path, int size) {
        return getIcon(path, size, true);
    }

    public static ImageIcon getIcon(String path, int size, boolean resourceFolder) {
        if (size == 0) size = cachedIconSize;
        // Return cached image if possible
        if (size == cachedIconSize && iconMap.containsKey(path)) {
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
            if (size == cachedIconSize) iconMap.put(path, icon);
            return icon;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ImageIcon getColorIcon(String path) {
        return getColorIcon(path, cachedIconSize);
    }

    public static ImageIcon getColorIcon(String path, int size) {
        if (size == 0) size = cachedIconSize;
        // Return cached image if possible
        if (size == cachedIconSize && colorIconMap.containsKey(path)) {
            return colorIconMap.get(path);
        }
        // Generate new image
        try {
            BufferedImage img = ImageIO.read(Objects.requireNonNull(ThemeManager.class.getResource(path)));
            ImageIcon icon = new ImageIcon(getColorImage(img, UIManager.getColor("Button.foreground")).getScaledInstance(size, size, Image.SCALE_SMOOTH));
            if (size == cachedIconSize) colorIconMap.put(path, icon);
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

    public static void setFont(String fontName) {
        if (fontName == null) return;
        if (fontName.equals(currentFontName)) return;
        fontStyleWasChanged = true;
        Stopwatch.start();
        if (fontName.equals(currentFontName)) return;
        if (!FontManager.isValidFont(fontName)) return;
        FontManager.setPreferredFont(fontName);
        refreshDefaultFonts();
        for (Component component : frames) {
            changeComponentFont(component, FontManager.getPreferredFont());
        }
        currentFontName = fontName;
    }

    private static void refreshDefaultFonts() {
        if (FontManager.USE_SYSTEM_DEFAULT) return;
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        String fontName = FontManager.getPreferredFont().getFontName();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                Font oldFont = (FontUIResource) value;
                FontUIResource fontResource = new FontUIResource(fontName, oldFont.getStyle(), SaveManager.settingsSaveFile.data.fontSize);
                UIManager.put(key, fontResource);
            }
        }
    }

    private static void changeComponentFont(Component component, Font font) {
        Font previousFont = component.getFont();
        component.setFont(font.deriveFont(previousFont.getStyle(), previousFont.getSize()));
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                changeComponentFont(child, font);
            }
        }
    }

    public static void setFontSize(int size) {
        if (size == currentFontSize) return;
        currentFontSize = size;
        fontSizeWasChanged = true;
        refreshDefaultFonts();
        for (Component frame : frames) {
            setFontSizeRecursive(frame, size);
//            SwingUtilities.updateComponentTreeUI(frame);
            frame.revalidate();
            frame.repaint();
        }
    }

    private static void setFontSizeRecursive(Component component, int size) {
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                setFontSizeRecursive(child, size);
            }
        }
        Font curFont = component.getFont();
        component.setFont(curFont.deriveFont(curFont.getStyle(), size));
    }

    public static int getCachedIconSize() {
        return cachedIconSize;
    }

    public static void setIconSize(int size) {
        assert (SwingUtilities.isEventDispatchThread());
        if (size == cachedIconSize) return;
        iconSizeWasChanged = true;
        cachedIconSize = size;
        iconMap.clear();
        colorIconMap.clear();
        for (Component frame : frames) {
//            setIconSizeRecursive(frame, size);
            // FIXME : updateComponentThread is very inefficient, should create an alternative function
            SwingUtilities.updateComponentTreeUI(frame);
            frame.revalidate();
            frame.repaint();
            // FIXME
            if (frame instanceof BasicDialog) {
                ((BasicDialog) frame).pack();
            }
        }
    }

    private static void setIconSizeRecursive(Component component, int size) {
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                setIconSizeRecursive(child, size);
            }
        }
        if (component instanceof IconButton) {
            ((IconButton) component).setIconSize(size);
        }
    }

    public static void recursiveUpdateUI(JComponent component) {
        component.updateUI();
        for (Component child : component.getComponents()) {
            recursiveUpdateUI((JComponent) child);
        }
    }

    public static void checkFontChange() {
        boolean fontWasChanged = fontStyleWasChanged || fontSizeWasChanged || iconSizeWasChanged;
        if (!fontWasChanged) return;
        for (IFontChangeListener listener : fontChangeListeners) listener.onFontChanged();
        if (fontStyleWasChanged)
            for (IDetailedFontChangeListener listener : detailedFontChangeListeners) listener.onFontStyleChanged();
        if (fontSizeWasChanged)
            for (IDetailedFontChangeListener listener : detailedFontChangeListeners) listener.onFontSizeChanged();
        if (iconSizeWasChanged)
            for (IDetailedFontChangeListener listener : detailedFontChangeListeners) listener.onIconSizeChanged();
        fontStyleWasChanged = false;
        fontSizeWasChanged = false;
        iconSizeWasChanged = false;
    }

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
     * Dumps all the Key, Value pairs from the UIManager into the clipboard.
     */
    public static void debugKeyValueDump() {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        StringBuilder builder = new StringBuilder();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            builder.append(key).append(" ::: ").append(value).append("\n");
        }
        StringSelection selection = new StringSelection(builder.toString());
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
    }

    // Listeners

    public static void addThemeListener(IThemeListener listener) {
        themeListeners.add(listener);
    }

    public static void removeThemeListener(IThemeListener listener) {
        themeListeners.remove(listener);
    }

    public static void addFontListener(IFontChangeListener listener) {
        fontChangeListeners.add(listener);
    }

    public static void removeFontChangeListener(IFontChangeListener listener) {
        fontChangeListeners.remove(listener);
    }

}
