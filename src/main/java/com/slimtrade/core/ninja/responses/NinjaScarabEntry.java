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
        if (cleanChaosValue == null) {
            float value = chaosValue >= 100 ? Math.round(chaosValue) : chaosValue;
            cleanChaosValue = ZUtil.formatNumberOneDecimal(value);
        }
        return cleanChaosValue;
    }

    @Override
    public String toString() {
        return cleanChaosValue() + "c";
    }

}
