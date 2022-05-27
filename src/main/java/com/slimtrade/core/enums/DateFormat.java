package com.slimtrade.core.enums;

public enum DateFormat {

    DD_MM("Day/Month", "dd/MM"),
    DD_MM_YY("Day/Month/Year", "dd/MM/YY"),
    MM_DD("Month/Day", "MM/dd"),
    MM_DD_YY("Month/Day/Year", "MM/dd/YY"),
    YY_MM_DD("Year/Month/Day", "YY/MM/dd"),
    ;

    private final String name;
    private final String format;

    DateFormat(String name, String format) {
        this.name = name;
        this.format = format;
    }

    public String toString() {
        return this.name;
    }

    public String getFormat() {
        return this.format;
    }


}
