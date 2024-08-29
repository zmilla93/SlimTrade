package com.slimtrade.core.enums;

public enum PathOfExileLeague {

    TEMP, HARDCORE_TEMP, RUTHLESS_TEMP, HC_RUTHLESS_TEMP,
    STANDARD, HARDCORE, RUTHLESS, HARDCORE_RUTHLESS,
    SSF_BTW;

    private final String name;
    // FIXME : Change this every league
    private static final String TEMP_LEAGUE = "Settlers";

    PathOfExileLeague() {
        String tempName = name();
        tempName = tempName.replace("_BTW", "");
        tempName = tempName.replace("TEMP", TEMP_LEAGUE);
        tempName = tempName.replace("STANDARD", "Standard");
        tempName = tempName.replace("HARDCORE", "Hardcore");
        tempName = tempName.replace("RUTHLESS", "Ruthless");
//        tempName = tempName.replace("_", "%20");
        tempName = tempName.replace("_", " ");
        name = tempName;
    }

    @Override
    public String toString() {
        return name;
    }

}
