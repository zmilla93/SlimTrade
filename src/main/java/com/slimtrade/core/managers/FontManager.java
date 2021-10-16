package com.slimtrade.core.managers;

import com.slimtrade.core.References;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class FontManager {

    public Font koreanBaseFont;
    public Font thaiBaseFont;

    public void loadFonts() {
        try {
            koreanBaseFont = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(getClass().getResourceAsStream("/font/IBMPlexSansKR/IBMPlexSansKR-Regular.ttf")));
            thaiBaseFont = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(getClass().getResourceAsStream("/font/IBMPlexSansThai/IBMPlexSansThai-Regular.ttf")));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    public Font getFont(String text, Font defaultFont) {
        if (text == null || text.matches("\\s*")) return defaultFont;
        String[] lines;
        if (text.contains("\n")) {
            lines = text.split("\\n");
        } else {
            lines = new String[]{text};
        }
        for (String str : lines) {
            if (str.matches("\\s*")) continue;
            int style = defaultFont != null ? defaultFont.getStyle() : 0;
            int size = defaultFont != null ? defaultFont.getSize() : 12;
            if (str.matches(References.KOREAN_CHAR_REGEX)) {
                return koreanBaseFont.deriveFont(style, size);
            } else if (str.matches(References.THAI_CHAR_REGEX)) {
                return thaiBaseFont.deriveFont(style, size);
            }
        }
        return defaultFont;

    }

}
