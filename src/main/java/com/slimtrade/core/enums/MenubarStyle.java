package com.slimtrade.core.enums;

import com.slimtrade.core.utility.ZUtil;

public enum MenubarStyle {

    DISABLED, TEXT, ICON;

    private final String name;

    MenubarStyle() {
        this.name = ZUtil.enumToString(name());
    }

    @Override
    public String toString() {
        return name;
    }

}
