package com.slimtrade.core.ninja;

import com.slimtrade.core.utility.ZUtil;

public class NinjaUtil {

    private NinjaUtil() {

    }

    public static String getChaosText(float chaosValue) {
        // FIXME : Should this threshold be adjustable?
        float value = chaosValue >= 50 ? Math.round(chaosValue) : chaosValue;
        String text = ZUtil.formatNumberOneDecimal(value) + "c";
        return text;
    }

    public static String getDivineValue(float divineValue) {
        String text;
        float value = divineValue >= 100 ? Math.round(divineValue) : divineValue;
        // Hide low values, otherwise most items simply show as "0.01" and the useful info gets flooded out
        if (value < 0.01) text = "";
        else text = value + "d";
        return text;
    }

}
