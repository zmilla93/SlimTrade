package com.slimtrade.core.enums;

import com.slimtrade.core.utility.ZUtil;

/**
 * Most of the currencies that you can list items for in a public stash tab.
 */
@Deprecated
public enum CommonCurrency {

    CHAOS_ORB,
    EXALTED_ORB,
    BLESSED_ORB,
    CARTOGRAPHERS_CHISEL("CARTOGRAPHER'S_CHISEL"),
    CHROMATIC_ORB,
    DIVINE_ORB,
    ENGINEERS_ORB("ENGINEER'S_ORB"),
    GEMCUTTERS_PRISM("GEMCUTTER'S_PRISM"),
    GLASSBLOWERS_BAUBLE("GLASSBLOWER'S_BAUBLE"),
    JEWELLERS_ORB("JEWELLER'S_ORB"),
    MIRROR_OF_KALANDRA,
    ORB_OF_ALCHEMY,
    ORB_OF_ALTERATION,
    ORB_OF_ANNULMENT,
    ORB_OF_AUGMENTATION,
    ORB_OF_CHANCE,
    ORB_OF_FUSING,
    ORB_OF_REGRET,
    ORB_OF_SCOURING,
    ORB_OF_TRANSMUTATION,
    REGAL_ORB,
    VAAL_ORB,
    ;

    private String name;

    CommonCurrency() {
        name = ZUtil.enumToString(name());
    }

    CommonCurrency(String name) {
        this.name = ZUtil.enumToString(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
