package com.slimtrade.core.enums;

public enum SpinnerRange {

    FONT_SIZE(10, 28, 12, 1),
    ICON_SIZE(14, 48, 20, 1),
    IGNORE_DURATION(0, 300, 60, 10),
    MESSAGES_BEFORE_COLLAPSE(1, 8, 3, 1),
    PRICE_THRESHOLD(0, 5000, 25, 1),
    ;

    public final int MIN;
    public final int MAX;
    public final int START;
    public final int STEP;

    SpinnerRange(int min, int max, int start, int step) {
        MIN = min;
        MAX = max;
        START = start;
        STEP = step;
    }

}
