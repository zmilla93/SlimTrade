package com.slimtrade.core.enums;

import com.slimtrade.core.utility.ZUtil;

public enum HistoryOrder {

    NEWEST_FIRST, NEWEST_LAST;

    private final String name;

    HistoryOrder() {
        this.name = ZUtil.enumToString(name());
    }

    @Override
    public String toString() {
        return name;
    }

}
