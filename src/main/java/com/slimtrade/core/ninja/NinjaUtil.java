package com.slimtrade.core.ninja;

import com.slimtrade.core.utility.ZUtil;

public class NinjaUtil {

    private NinjaUtil() {
        /// Static class
    }

    public static String getChaosText(float chaosValue) {
        if (chaosValue < 0.05) return "";
        // FIXME : Should this threshold be adjustable?
        float value = chaosValue >= 10 ? Math.round(chaosValue) : chaosValue;
        boolean thousand = false;
        if (value > 1000) {
            value = Math.round(value / 1000);
            thousand = true;
        }
        String k = thousand ? "k" : "";
        String text = ZUtil.formatNumberOneDecimal(value) + k + "";
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
