package com.slimtrade.core.enums;

import com.slimtrade.core.utility.ZUtil;

public enum MatchType {

    EXACT_MATCH, CONTAINS_TEXT;

    private final String name;

    MatchType() {
        name = ZUtil.enumToString(name());
    }

    @Override
    public String toString() {
        return name;
    }

}
