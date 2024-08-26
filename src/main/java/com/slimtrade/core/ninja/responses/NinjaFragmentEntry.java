package com.slimtrade.core.ninja.responses;

public class NinjaFragmentEntry {

    public String currencyTypeName;
    public NinjaPayment pay;
    public NinjaPayment receive;
    public float chaosEquivalent;

    @Override
    public String toString() {
        return chaosEquivalent + "c";
    }

}
