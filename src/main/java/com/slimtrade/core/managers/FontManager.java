package com.slimtrade.core.managers;

import com.slimtrade.core.enums.FontLanguage;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class FontManager {

    public static Font koreanBaseFont;
    public static Font thaiBaseFont;
    private static Font defaultFont;

    public static void loadFonts() {
        try {
//            englishBaseFont = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(FontManager.class.getResourceAsStream("/font/IBMPlexSans-Regular.ttf")));
            koreanBaseFont = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(FontManager.class.getResourceAsStream("/font/IBMPlexSansKR/IBMPlexSansKR-Regular.ttf")));
            thaiBaseFont = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(FontManager.class.getResourceAsStream("/font/IBMPlexSansThai/IBMPlexSansThai-Regular.ttf")));
        } catch (FontFormatException | IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Font getFont(String text, Font defaultFont) {
        if (text == null || text.matches("\\s*")) return defaultFont;
        int style = defaultFont != null ? defaultFont.getStyle() : Font.PLAIN;
        int size = defaultFont != null ? defaultFont.getSize() : 12;
        for (int i = 0; i < text.length(); i++) {
            int val = text.charAt(i);
            if (val >= 3584 && val <= 3711) {
                return thaiBaseFont.deriveFont(style, size);
            } else if ((val >= 44032 && val <= 55203) || //Hangul Syllables: U+AC00–U+D7A3
                    (val >= 4352 && val <= 4607) || //Hangul Jamo: U+1100–U+11FF
                    (val >= 43360 && val <= 43391) || //Hangul Jamo Extended-A: U+A960–U+A97F
                    (val >= 55216 && val <= 55295) || //Hangul Jamo Extended-B: U+D7B0–U+D7FF
                    (val >= 12592 && val <= 12687)) { //Hangul Compatibility Jamo: U+3130–U+318F
                return koreanBaseFont.deriveFont(style, size);
            }
        }
        return defaultFont;
    }

    public static Font getFont(Font font, FontLanguage language) {
        if (language == FontLanguage.KOREAN) return koreanBaseFont.deriveFont(font.getStyle(), font.getSize());
        if (language == FontLanguage.THAI) return thaiBaseFont.deriveFont(font.getStyle(), font.getSize());
        return defaultFont.deriveFont(font.getStyle(), font.getSize());
    }

    public static FontLanguage getFontLanguage(String text) {
        if (text == null || text.matches("\\s*")) return FontLanguage.DEFAULT;
//        int style = defaultFont != null ? defaultFont.getStyle() : Font.PLAIN;
//        int size = defaultFont != null ? defaultFont.getSize() : 12;
        for (int i = 0; i < text.length(); i++) {
            int val = text.charAt(i);
            if (val >= 3584 && val <= 3711) {
                return FontLanguage.THAI;
            } else if ((val >= 44032 && val <= 55203) || //Hangul Syllables: U+AC00–U+D7A3
                    (val >= 4352 && val <= 4607) || //Hangul Jamo: U+1100–U+11FF
                    (val >= 43360 && val <= 43391) || //Hangul Jamo Extended-A: U+A960–U+A97F
                    (val >= 55216 && val <= 55295) || //Hangul Jamo Extended-B: U+D7B0–U+D7FF
                    (val >= 12592 && val <= 12687)) { //Hangul Compatibility Jamo: U+3130–U+318F
                return FontLanguage.KOREAN;
            }
        }
        return FontLanguage.DEFAULT;
    }

    // FIXME (Minor) : default font is just set to whatever font is first.
    //  Works for now, will break if default font is changed at runtime
    public static JLabel applyFont(JLabel component) {
        if (defaultFont == null) defaultFont = component.getFont();
        FontLanguage language = getFontLanguage(component.getText());
        component.setFont(getFont(component.getFont(), language));
        return component;
    }

    public static JButton applyFont(JButton component) {
        if (defaultFont == null) defaultFont = component.getFont();
        FontLanguage language = getFontLanguage(component.getText());
        component.setFont(getFont(component.getFont(), language));
        return component;
    }

    public static JTextField applyFont(JTextField component) {
        if (defaultFont == null) defaultFont = component.getFont();
        FontLanguage language = getFontLanguage(component.getText());
        component.setFont(getFont(component.getFont(), language));
        return component;
    }

}
