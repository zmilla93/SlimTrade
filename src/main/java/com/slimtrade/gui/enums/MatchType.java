package com.slimtrade.gui.enums;

public enum MatchType {

    EXACT("Exact Match"),
    CONTAINS("Contains Text");

    private MatchType(String name) {
        this.name = name;
    }

    private String name;

    public String toString() {
        return name;
    }
}
