package com.slimtrade.gui.options.stash;

import com.slimtrade.core.utility.ZUtil;

public enum StashTabType {

    NORMAL, QUAD;

    private final String name;

    StashTabType() {
        name = ZUtil.enumToString(name());
    }

    @Override
    public String toString() {
        return name;
    }

}
