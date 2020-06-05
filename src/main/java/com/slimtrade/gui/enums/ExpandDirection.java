package com.slimtrade.gui.enums;

public enum ExpandDirection {

    UP("Upwards"),
    DOWN("Downwards");

    private final String name;

    ExpandDirection(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}
