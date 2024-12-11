package github.zmilla93.core.ninja.responses;

import github.zmilla93.core.ninja.NinjaUtil;

public class NinjaFragmentEntry implements INinjaEntry {

    public String currencyTypeName;
    public NinjaPayment pay;
    public NinjaPayment receive;
    public float chaosEquivalent;
    private String chaosText;
    private String divineText;

    @Override
    public String toString() {
        return chaosEquivalent + "c";
    }

    @Override
    public String getName() {
        return currencyTypeName;
    }

    @Override
    public float getChaosValue() {
        return chaosEquivalent;
    }

    @Override
    public String getChaosText() {
        if (chaosText == null) chaosText = NinjaUtil.getChaosText(chaosEquivalent);
        return chaosText;
    }

    @Override
    public float getDivineValue() {
        // TODO : this
        return -1;
    }

    @Override
    public String getDivineText() {
        if (divineText == null) divineText = NinjaUtil.getChaosText(chaosEquivalent);
        return divineText;
    }

}
