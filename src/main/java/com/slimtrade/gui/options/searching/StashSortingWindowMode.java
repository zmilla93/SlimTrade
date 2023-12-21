package com.slimtrade.gui.options.searching;

import com.slimtrade.core.utility.ZUtil;

public enum StashSortingWindowMode {

    COMBINED, SEPARATE;

    private final String cleanName;

    StashSortingWindowMode() {
        cleanName = ZUtil.enumToString(name());
    }

    @Override
    public String toString() {
        return cleanName;
    }

}
