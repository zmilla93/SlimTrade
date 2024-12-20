package github.zmilla93.core.ninja;

import github.zmilla93.core.utility.ZUtil;

public enum NinjaTabType {

    CURRENCY(new String[]{"Normal", "Exotic"}, NinjaEndpoint.CURRENCY),
    FRAGMENT(new String[]{"General", "Scarab", "Breach", "Eldritch"}, NinjaEndpoint.FRAGMENT, NinjaEndpoint.SCARAB),
    ESSENCE(NinjaEndpoint.ESSENCE),
    DELVE(NinjaEndpoint.DELVE, NinjaEndpoint.RESONATOR),
    BLIGHT(NinjaEndpoint.BLIGHT),
    DELIRIUM(NinjaEndpoint.DELIRIUM),
    ULTIMATUM(NinjaEndpoint.CURRENCY);

    public final String[] subTabs;
    private final String name;
    public final NinjaEndpoint[] dependencies;

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
