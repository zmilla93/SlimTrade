package github.zmilla93.core.ninja;

import github.zmilla93.core.enums.PathOfExileLeague;

public enum NinjaEndpoint {

    CURRENCY("Currency", NinjaOverview.CURRENCY),
    ESSENCE("Essence", NinjaOverview.ITEM),
    FRAGMENT("Fragment", NinjaOverview.CURRENCY),
    SCARAB("Scarab", NinjaOverview.ITEM),
    DELVE("Fossil", NinjaOverview.ITEM),
    RESONATOR("Resonator", NinjaOverview.ITEM),
    BLIGHT("Oil", NinjaOverview.ITEM),
    DELIRIUM("DeliriumOrb", NinjaOverview.ITEM),
    // Note: Ultimatum catalysts are in Currency
    ;

    public final String type;
    public final NinjaOverview overview;

    NinjaEndpoint(String type, NinjaOverview overview) {
        this.type = type;
        this.overview = overview;
    }

    public String getURL(PathOfExileLeague league) {
        return "https://poe.ninja/api/data/" + overview + "?league=" + league + "&type=" + type;
    }

}
