package com.slimtrade.core.enums;

import com.slimtrade.core.utility.ZUtil;

public enum MatchType {

    CONTAINS_TEXT, EXACT_MATCH;

    private final String name;

    MatchType() {
        name = ZUtil.enumToString(name());
    }

    @Override
    public String toString() {
        return name;
    }

}
