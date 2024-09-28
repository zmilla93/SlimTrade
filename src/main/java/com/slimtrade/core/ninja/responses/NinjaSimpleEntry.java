package com.slimtrade.core.ninja.responses;

import com.slimtrade.core.ninja.NinjaUtil;

public class NinjaSimpleEntry implements INinjaEntry {

    public final String name;
    public final float chaosValue;
    public final float divineValue;
    private String chaosText;
    private String divineText;

    public NinjaSimpleEntry(String name, float chaosValue, float divineValue) {
        this.name = name;
        this.chaosValue = chaosValue;
        this.divineValue = divineValue;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public float getChaosValue() {
        return chaosValue;
    }

    @Override
    public String getChaosText() {
        if (chaosText == null) chaosText = NinjaUtil.getChaosText(chaosValue);
        return chaosText;
    }

    @Override
    public float getDivineValue() {
        return divineValue;
    }

    @Override
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
