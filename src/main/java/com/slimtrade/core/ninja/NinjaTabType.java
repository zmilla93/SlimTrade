package com.slimtrade.core.ninja;

import com.slimtrade.core.utility.ZUtil;

public enum NinjaTabType {

    CURRENCY("Normal", "Exotic"),
    FRAGMENTS("General", "Scarab", "Breach", "Eldritch"),
    ESSENCE,
    DELVE,
    BLIGHT,
    DELIRIUM,
    ULTIMATUM;

    public final String[] subTabs;
    private final String name;

    NinjaTabType() {
        name = ZUtil.enumToString(name());
        subTabs = null;
    }

    NinjaTabType(String... subTabs) {
        name = ZUtil.enumToString(name());
        this.subTabs = subTabs;
    }

    @Override
    public String toString() {
        return name;
    }

}
