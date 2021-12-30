package com.slimtrade.core.enums;

import com.slimtrade.core.utility.SlimUtil;

public enum ButtonRow {

    TOP_ROW,
    BOTTOM_ROW,
    ;


    @Override
    public String toString() {
        return SlimUtil.enumToString(name());
    }
}
