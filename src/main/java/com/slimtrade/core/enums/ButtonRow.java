package com.slimtrade.core.enums;

import com.slimtrade.core.utility.ZUtil;

public enum ButtonRow {

    TOP_ROW,
    BOTTOM_ROW,
    ;


    @Override
    public String toString() {
        return ZUtil.enumToString(name());
    }
}
