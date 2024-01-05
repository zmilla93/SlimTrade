package com.slimtrade.core.data;

import com.slimtrade.core.managers.FontManager;

import java.awt.*;

/**
 * Checks supported languages for a given font.
 */
public class FontLanguageSupport {

    public final boolean english;
    public final boolean chinese;
    public final boolean korean;
    public final boolean thai;
    public final boolean russian;

    public FontLanguageSupport(Font font) {
        english = font.canDisplayUpTo(FontManager.ENGLISH_EXAMPLE_TEXT) == -1;
        chinese = font.canDisplayUpTo(FontManager.CHINESE_EXAMPLE_TEXT) == -1;
        korean = font.canDisplayUpTo(FontManager.KOREAN_EXAMPLE_TEXT) == -1;
        thai = font.canDisplayUpTo(FontManager.THAI_EXAMPLE_TEXT) == -1;
        russian = font.canDisplayUpTo(FontManager.RUSSIAN_EXAMPLE_TEXT) == -1;
    }

}
