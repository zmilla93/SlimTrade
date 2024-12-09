package com.slimtrade.core.poe;

/**
 * An enum for knowing which version of Path of Exile is being referenced.
 * The toString() value also matches the name of the Path of Exile's install folder, and the game's window title.
 */
public enum Game {

    PATH_OF_EXILE_1("Path of Exile"), PATH_OF_EXILE_2("Path of Exile 2");

    private final String name;

    Game(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
