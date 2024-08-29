package com.slimtrade.core.ninja;

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
