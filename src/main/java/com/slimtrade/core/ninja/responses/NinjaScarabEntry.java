package com.slimtrade.core.ninja.responses;

import com.slimtrade.core.utility.ZUtil;

public class NinjaScarabEntry implements INinjaEntry {

    public final String name;
    public final float chaosValue;
    public final float divineValue;
    private String chaosText;
    private String divineText;

    public NinjaScarabEntry(String name, float chaosValue, float divineValue) {
        this.name = name;
        this.chaosValue = chaosValue;
        this.divineValue = divineValue;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getChaosText() {
        if (chaosText == null) {
            // Remove numbers above a threshold to save screen space
            // FIXME : Should this threshold be adjustable?
            float value = chaosValue >= 50 ? Math.round(chaosValue) : chaosValue;
            chaosText = ZUtil.formatNumberOneDecimal(value) + "c";
        }
        return chaosText;
    }

    public String getDivineText() {
        if (divineText == null) {
            float value = divineValue >= 100 ? Math.round(divineValue) : divineValue;
            // Hide low values, otherwise most items simply show as "0.01" and the useful info gets flooded out
            if (value < 0.01) divineText = "";
            else divineText = value + "d";
        }
        return divineText;
    }

    @Override
    public String toString() {
        return getChaosText();
    }

}
