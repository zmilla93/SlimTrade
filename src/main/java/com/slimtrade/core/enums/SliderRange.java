package com.slimtrade.core.enums;

public enum SliderRange {

    CHEAT_SHEET_OPACITY(20, 100, 100),
    CHEAT_SHEET_SCALE(20, 100, 100),
    FADED_OPACITY(10, 90, 90),
    ;

    public final int MIN;
    public final int MAX;
    public final int START;

    SliderRange(int min, int max, int start) {
        MIN = min;
        MAX = max;
        START = start;
    }

}
