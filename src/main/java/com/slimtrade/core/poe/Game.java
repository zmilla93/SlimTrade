package com.slimtrade.core.poe;

/**
 * An enum for knowing which version of Path of Exile is being referenced.
 */
public enum Game {

    PATH_OF_EXILE_1("Path of Exile 1"), PATH_OF_EXILE_2("Path of Exile 2");

    private final String name;

    Game(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
