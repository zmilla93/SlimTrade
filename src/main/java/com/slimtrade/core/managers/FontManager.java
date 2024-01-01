package com.slimtrade.core.managers;

import com.slimtrade.core.enums.FontLanguage;
import com.slimtrade.core.language.UnicodeRange;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

/**
 * Handles loading fonts and font selection for foreign languages.
 * Applying the preferred font to the UIManager is handled in the ThemeManager.
 *
 * @see com.slimtrade.modules.theme.ThemeManager
 */
public class FontManager {

    public static final boolean USE_SYSTEM_DEFAULT = false;

    public static final String CHINESE_EXAMPLE_TEXT = "Chinese - 语言测试";
    public static final String JAPANESE_EXAMPLE_TEXT = "Japanese - 語学テスト";
    public static final String KOREAN_EXAMPLE_TEXT = "Korean - 어학시험";
    public static final String RUSSIAN_EXAMPLE_TEXT = "Russian - Языковой тест";
    public static final String THAI_EXAMPLE_TEXT = "Thai - แบบทดสอบภาษา";

    private static Font systemFont;
    private static Font preferredFont;
    private static Font koreanFont;
    private static Font thaiFont;

    private static boolean systemFontSupportsChinese;
    private static boolean systemFontSupportsKorean;
    private static boolean systemFontSupportsThai;
    private static boolean systemFontSupportsRussian;

    private static boolean preferredFontSupportsChinese;
    private static boolean preferredFontSupportsKorean;
    private static boolean preferredFontSupportsThai;
    private static boolean preferredFontSupportsRussian;

    public static Font getPreferredFont() {
        return preferredFont;
    }

    public static void loadFonts() {
        systemFont = UIManager.getFont("Label.font");
        systemFontSupportsChinese = systemFont.canDisplayUpTo(CHINESE_EXAMPLE_TEXT) == -1;
        systemFontSupportsKorean = systemFont.canDisplayUpTo(KOREAN_EXAMPLE_TEXT) == -1;
        systemFontSupportsThai = systemFont.canDisplayUpTo(THAI_EXAMPLE_TEXT) == -1;
        systemFontSupportsRussian = systemFont.canDisplayUpTo(RUSSIAN_EXAMPLE_TEXT) == -1;

        try {
            preferredFont = new Font("Arial", Font.PLAIN, 12);
            koreanFont = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(FontManager.class.getResourceAsStream("/font/IBMPlexSansKR/IBMPlexSansKR-Regular.ttf")));
            thaiFont = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(FontManager.class.getResourceAsStream("/font/IBMPlexSansThai/IBMPlexSansThai-Regular.ttf")));
        } catch (FontFormatException | IOException ex) {
            ex.printStackTrace();
        }
        checkPreferredFontLanguageSupport();
        if (preferredFont.getFontName().equals("Dialog.plain"))
            System.err.println("[FontManager] Loaded font does not exist!");
    }

    private static void checkPreferredFontLanguageSupport() {
        preferredFontSupportsChinese = preferredFont.canDisplayUpTo(CHINESE_EXAMPLE_TEXT) == -1;
        preferredFontSupportsKorean = preferredFont.canDisplayUpTo(KOREAN_EXAMPLE_TEXT) == -1;
        preferredFontSupportsThai = preferredFont.canDisplayUpTo(THAI_EXAMPLE_TEXT) == -1;
        preferredFontSupportsRussian = preferredFont.canDisplayUpTo(RUSSIAN_EXAMPLE_TEXT) == -1;
    }

    private static Font getFont(Font font, FontLanguage language) {
        switch (language) {
            case CHINESE:
                if (preferredFontSupportsChinese) return preferredFont;
                if (systemFontSupportsChinese) return systemFont;
                break;
            case KOREAN:
                if (preferredFontSupportsKorean) return preferredFont;
                if (systemFontSupportsKorean) return systemFont;
                return koreanFont.deriveFont(font.getStyle(), font.getSize());
            case THAI:
                if (preferredFontSupportsThai) return preferredFont;
                if (systemFontSupportsThai) return systemFont;
                return thaiFont.deriveFont(font.getStyle(), font.getSize());
            case RUSSIAN:
                if (preferredFontSupportsRussian) return preferredFont;
                if (systemFontSupportsRussian) return systemFont;
                break;
        }
        if (USE_SYSTEM_DEFAULT) return font;
        return preferredFont.deriveFont(font.getStyle(), font.getSize());
    }

    private static FontLanguage getFontLanguage2(String text) {
        if (text == null || text.matches("\\s*")) return FontLanguage.DEFAULT;
        for (int i = 0; i < text.length(); i++) {
//            int val = text.charAt(i);
            char val = text.charAt(i);
            if (val >= 0x0E00 && val <= 0x0E7F) {
                return FontLanguage.THAI;
            } else if ((val >= 44032 && val <= 55203) ||    //Hangul Syllables: U+AC00–U+D7A3
                    (val >= 4352 && val <= 4607) ||         //Hangul Jamo: U+1100–U+11FF
                    (val >= 43360 && val <= 43391) ||       //Hangul Jamo Extended-A: U+A960–U+A97F
                    (val >= 55216 && val <= 55295) ||       //Hangul Jamo Extended-B: U+D7B0–U+D7FF
                    (val >= 12592 && val <= 12687)) {       //Hangul Compatibility Jamo: U+3130–U+318F
                return FontLanguage.KOREAN;
            }
        }
        return FontLanguage.DEFAULT;
    }

    // FIXME (Minor) : default font is just set to whatever font is first.
    //  Works for now, will break if default font is changed at runtime
    public static JLabel applyFont(JLabel component) {
        if (preferredFont == null) preferredFont = component.getFont();
        FontLanguage language = UnicodeRange.getLanguage(component.getText());
        component.setFont(getFont(component.getFont(), language));
        return component;
    }

    public static JButton applyFont(JButton component) {
        if (preferredFont == null) preferredFont = component.getFont();
        FontLanguage language = UnicodeRange.getLanguage(component.getText());
        component.setFont(getFont(component.getFont(), language));
        return component;
    }

    public static JTextField applyFont(JTextField component) {
        if (preferredFont == null) preferredFont = component.getFont();
        FontLanguage language = UnicodeRange.getLanguage(component.getText());
        component.setFont(getFont(component.getFont(), language));
        return component;
    }

}
