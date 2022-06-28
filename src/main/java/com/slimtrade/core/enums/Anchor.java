package com.slimtrade.core.enums;

import com.slimtrade.core.utility.ZUtil;

public enum Anchor {

    TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT;

    private final String name;

    Anchor() {
        this.name = ZUtil.enumToString(name());
    }

    @Override
    public String toString() {
        return name;
    }

}
