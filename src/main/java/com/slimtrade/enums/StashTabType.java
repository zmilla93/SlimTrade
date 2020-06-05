package com.slimtrade.enums;

public enum StashTabType {

    NORMAL("Normal"),
    QUAD("Quad"),
    ;

    private String name;

    StashTabType(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

}
