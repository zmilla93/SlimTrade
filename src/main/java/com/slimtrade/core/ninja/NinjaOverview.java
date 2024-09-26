package com.slimtrade.core.ninja;

/**
 * The 'overview' is just part of a poe.ninja API endpoint.
 */
public enum NinjaOverview {

    CURRENCY, ITEM;

    private final String name;

    NinjaOverview() {
        name = name().toLowerCase() + "overview";
    }

    @Override
    public String toString() {
        return name;
    }

}
