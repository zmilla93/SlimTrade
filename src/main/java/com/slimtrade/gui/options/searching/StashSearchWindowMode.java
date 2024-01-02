package com.slimtrade.gui.options.searching;

import com.slimtrade.core.utility.ZUtil;

public enum StashSearchWindowMode {

    COMBINED, SEPARATE;

    private final String cleanName;

    StashSearchWindowMode() {
        cleanName = ZUtil.enumToString(name());
    }

    @Override
    public String toString() {
        return cleanName;
    }

}
