package com.slimtrade.core.managers;

import com.slimtrade.core.data.FontLanguageSupport;
import com.slimtrade.core.enums.FontLanguage;
import com.slimtrade.core.language.UnicodeRange;
import com.slimtrade.modules.updater.ZLogger;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

/**
 * Handles loading fonts and font selection for foreign languages.
 * Applying the preferred font to the UIManager is handled in the ThemeManager.
 *
 * @see com.slimtrade.modules.theme.ThemeManager
 */
public class FontManager {

    public static final boolean USE_SYSTEM_DEFAULT = false;
    public static String DEFAULT_FONT = "Arial";

    public static final String ENGLISH_EXAMPLE_TEXT = "English - Language Test";
    public static final String CHINESE_EXAMPLE_TEXT = "Chinese - 语言测试";
    public static final String JAPANESE_EXAMPLE_TEXT = "Japanese - 語学テスト";
    public static final String KOREAN_EXAMPLE_TEXT = "Korean - 어학시험";
    public static final String RUSSIAN_EXAMPLE_TEXT = "Russian - Языковой тест";
    public static final String THAI_EXAMPLE_TEXT = "Thai - แบบทดสอบภาษา";

    private static Font systemFont;
    private static Font preferredFont;
    private static Font koreanFont;
    private static Font thaiFont;

    private static FontLanguageSupport systemFontSupport;
    private static FontLanguageSupport preferredFontSupport;

    private static final ArrayList<String> validFonts = new ArrayList<>();
    private static final HashSet<String> fontBlacklist = new HashSet<>();

    static {
        fontBlacklist.add("Gabriola");
        fontBlacklist.add("Microsoft Himalaya");
        fontBlacklist.add("Microsoft Yi Baiti");
    }

    public static void loadFonts() {
        systemFont = UIManager.getFont("Label.font");
        systemFontSupport = new FontLanguageSupport(systemFont);
        try {
            preferredFont = new Font(DEFAULT_FONT, Font.PLAIN, 12);
            koreanFont = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(FontManager.class.getResourceAsStream("/font/IBMPlexSansKR-Regular.ttf")));
            thaiFont = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(FontManager.class.getResourceAsStream("/font/IBMPlexSansThai-Regular.ttf")));
        } catch (FontFormatException | IOException ex) {
            ZLogger.log("Failed to initialize fonts!");
            ZLogger.log(ex.getStackTrace());
        }
        checkPreferredFontLanguageSupport();
        if (preferredFont.getFontName().equals("Dialog.plain"))
            ZLogger.err("[FontManager] Loaded font does not exist!");
    }

    private static void checkPreferredFontLanguageSupport() {
        preferredFontSupport = new FontLanguageSupport(preferredFont);
    }

    public static void setPreferredFont(String fontName) {
        preferredFont = new Font(fontName, Font.PLAIN, 12);
        checkPreferredFontLanguageSupport();
    }

    public static Font getPreferredFont() {
        return preferredFont;
    }

    private static Font getFont(Font font, FontLanguage language) {
        Font languageFont = getFont(language);
        return languageFont.deriveFont(font.getStyle(), font.getSize());
    }

    private static Font getFont(FontLanguage language) {
        switch (language) {
            case CHINESE:
                if (preferredFontSupport.chinese) return preferredFont;
                if (systemFontSupport.chinese) return systemFont;
                break;
            case KOREAN:
                if (preferredFontSupport.korean) return preferredFont;
                if (systemFontSupport.korean) return systemFont;
                return koreanFont;
            case THAI:
                if (preferredFontSupport.thai) return preferredFont;
                if (systemFontSupport.thai) return systemFont;
                return thaiFont;
            case RUSSIAN:
                if (preferredFontSupport.russian) return preferredFont;
                if (systemFontSupport.russian) return systemFont;
                break;
        }
        if (USE_SYSTEM_DEFAULT) return systemFont;
        return preferredFont;
    }

    public static ArrayList<String> getAllFonts() {
        if (validFonts.size() > 0) return validFonts;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        for (String fontName : ge.getAvailableFontFamilyNames()) {
            Font font = new Font(fontName, Font.PLAIN, 12);
            FontLanguageSupport languageSupport = new FontLanguageSupport(font);
            if (fontBlacklist.contains(fontName)) continue;
            if (languageSupport.english) validFonts.add(fontName);
        }
        return validFonts;
    }

    public static boolean isValidFont(String targetFont) {
        for (String fontName : getAllFonts()) {
            if (fontName.equals(targetFont)) return true;
        }
        return false;
    }

    public static JLabel applyFont(JLabel component) {
        if (component == null) return null;
        FontLanguage language = UnicodeRange.getLanguage(component.getText());
        component.setFont(getFont(component.getFont(), language));
        return component;
    }

    public static JButton applyFont(JButton component) {
        if (component == null) return null;
        FontLanguage language = UnicodeRange.getLanguage(component.getText());
        component.setFont(getFont(component.getFont(), language));
        return component;
    }

    public static JTextField applyFont(JTextField component) {
        if (component == null) return null;
        FontLanguage language = UnicodeRange.getLanguage(component.getText());
        component.setFont(getFont(component.getFont(), language));
        return component;
    }

}
