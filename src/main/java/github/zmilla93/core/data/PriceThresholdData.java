package github.zmilla93.core.data;

import github.zmilla93.core.audio.SoundComponent;
import github.zmilla93.core.enums.CurrencyType;

public class PriceThresholdData implements Comparable<PriceThresholdData> {

    public final CurrencyType currencyType;
    public final int quantity;
    public final SoundComponent soundComponent;

    public PriceThresholdData(CurrencyType currencyType, int quantity, SoundComponent soundComponent) {
        this.currencyType = currencyType;
        this.quantity = quantity;
        this.soundComponent = soundComponent;
    }

    @Override
    public int compareTo(PriceThresholdData o) {
        return quantity - o.quantity;
    }
}

