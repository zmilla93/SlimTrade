package com.slimtrade.core.enums;

import com.slimtrade.core.utility.ZUtil;

public enum ExpandDirection {

    UPWARDS, DOWNWARDS;

    private final String name;

    ExpandDirection() {
        name = ZUtil.enumToString(name());
    }

    @Override
    public String toString() {
        return name;
    }
}
