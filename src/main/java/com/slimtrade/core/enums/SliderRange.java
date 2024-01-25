package com.slimtrade.core.enums;

public enum SliderRange {

    AUDIO_VOLUME(0, 100, 50),
    CHEAT_SHEET_OPACITY(20, 100, 100),
    CHEAT_SHEET_SCALE(20, 100, 100),
    FADED_OPACITY(10, 90, 50),
    MESSAGE_WIDTH(350, 500, 400),
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
