package com.slimtrade.core.enums;

import com.slimtrade.core.utility.ZUtil;

public enum MacroButtonType {

    ICON, TEXT;

    private final String name;

    MacroButtonType() {
        name = ZUtil.enumToString(name());
    }

    @Override
    public String toString() {
        return name;
    }
}
