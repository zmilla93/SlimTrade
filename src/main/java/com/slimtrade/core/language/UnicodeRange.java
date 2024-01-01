package com.slimtrade.core.language;

import com.slimtrade.core.enums.FontLanguage;

import java.util.ArrayList;

/**
 * Stores unicode ranges of various languages. Used to get an appropriate font for a piece of text.
 * <a href="https://www.unicode.org/charts/">Unicode Charts</a>
 */
public class UnicodeRange {

    public static final UnicodeRange CHINESE = new UnicodeRange(FontLanguage.CHINESE);
    public static final UnicodeRange KOREAN = new UnicodeRange(FontLanguage.KOREAN);
    public static final UnicodeRange THAI = new UnicodeRange(FontLanguage.THAI);
    public static final UnicodeRange RUSSIAN = new UnicodeRange(FontLanguage.RUSSIAN);
    public static final ArrayList<UnicodeRange> ALL_RANGES = new ArrayList<>();

    static {
        // https://www.unicode.org/charts/

        // Chinese (Should also cover Japanese)
        // Note: There are more chinese character ranges, but this covers the most common ones
        // https://stackoverflow.com/questions/1366068/whats-the-complete-range-for-chinese-characters-in-unicode
        CHINESE.addRange(new Range(0x4E00, 0x9FFF));    // CJK Unified Ideographs (Han)
        CHINESE.addRange(new Range(0x3400, 0x4DBF));    // CJK Extension A
        CHINESE.addRange(new Range(0x2B740, 0x2B81F));  // CJK Extension D
        CHINESE.addRange(new Range(0x3000, 0x303F));    // CJK Symbols and Punctuation

        // Korean
        KOREAN.addRange(new Range(0x1100, 0x11FF)); //Hangul Jamo
        KOREAN.addRange(new Range(0xA960, 0xA97F)); //Hangul Jamo Extended-A
        KOREAN.addRange(new Range(0xD7B0, 0xD7FF)); //Hangul Jamo Extended-B
        KOREAN.addRange(new Range(0x3130, 0x318F)); //Hangul Compatibility Jamo
        KOREAN.addRange(new Range(0xAC00, 0xD7A3)); //Hangul Syllables

        // Thai
        THAI.addRange(new Range(0x0E00, 0x0E7F));

        // Russian
        RUSSIAN.addRange(new Range(0x0400, 0x04FF)); // Cyrillic

        // NOTE : These are added in the order they should be checked
        ALL_RANGES.add(THAI);
        ALL_RANGES.add(RUSSIAN);
        ALL_RANGES.add(KOREAN);
        ALL_RANGES.add(CHINESE);
    }

    private final ArrayList<Range> rangeList = new ArrayList<>();
    public final FontLanguage language;

    public UnicodeRange(FontLanguage language) {
        this.language = language;
    }

    public void addRange(Range range) {
        rangeList.add(range);
    }

    public boolean contains(char c) {
        return (contains((int) c));
    }

    public boolean contains(int i) {
        for (Range range : rangeList) {
            if (range.contains(i)) return true;
        }
        return false;
    }

    public static FontLanguage getLanguage(String text) {
        for (char c : text.toCharArray()) {
            for (UnicodeRange range : ALL_RANGES) {
                if (range.contains(c)) return range.language;
            }
        }
        return FontLanguage.DEFAULT;
    }

}
