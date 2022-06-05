package com.slimtrade.core.enums;

public enum SpinnerRangeFloat {

    SECONDS_BEFORE_FADE(0, 20, 6, 0.5f),
    ;

    public final float MIN;
    public final float MAX;
    public final float START;
    public final float STEP;

    SpinnerRangeFloat(float min, float max, float start, float step) {
        MIN = min;
        MAX = max;
        START = start;
        STEP = step;
    }

}
