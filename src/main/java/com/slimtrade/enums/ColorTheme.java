package com.slimtrade.enums;

public enum ColorTheme {

    SOLARIZED_LIGHT("Solarized Light"),
    SOLARIZED_DARK("Solarized Dark"),
    STORMY("Stormy"),
    MONOKAI("Monokai"),
    ;

    private String name;

    ColorTheme(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

}
