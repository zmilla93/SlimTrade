package com.slimtrade.core.ninja.responses;

import com.slimtrade.core.utility.ZUtil;

public class NinjaScarabEntry {

    public final String name;
    public final float chaosValue;
    public final float divineValue;
    private String cleanChaosValue;
    private String cleanDivineValue;

    public NinjaScarabEntry(String name, float chaosValue, float divineValue) {
        this.name = name;
        this.chaosValue = chaosValue;
        this.divineValue = divineValue;
    }

    public String cleanChaosValue() {
        if (cleanChaosValue == null) cleanChaosValue = ZUtil.formatNumberOneDecimal(chaosValue);
        return cleanChaosValue;
    }

}
