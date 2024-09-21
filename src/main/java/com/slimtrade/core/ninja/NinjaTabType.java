package com.slimtrade.core.ninja;

import com.slimtrade.core.utility.ZUtil;

public enum NinjaTabType {

    CURRENCY(new String[]{"Normal", "Exotic"}, NinjaEndpoint.CURRENCY),
    FRAGMENTS(new String[]{"General", "Scarab", "Breach", "Eldritch"}, NinjaEndpoint.FRAGMENTS),
    ESSENCE(NinjaEndpoint.ESSENCE),
    DELVE,
    BLIGHT,
    DELIRIUM,
    ULTIMATUM;

    public final String[] subTabs;
    private final String name;
    private final NinjaEndpoint[] dependencies;

    NinjaTabType(NinjaEndpoint... dependencies) {
        name = ZUtil.enumToString(name());
        subTabs = null;
        this.dependencies = dependencies;
    }

    NinjaTabType(String[] subTabs, NinjaEndpoint... dependencies) {
        name = ZUtil.enumToString(name());
        this.subTabs = subTabs;
        this.dependencies = dependencies;
    }

    @Override
    public String toString() {
        return name;
    }

}
