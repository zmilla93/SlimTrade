package com.slimtrade.core.data;

import com.slimtrade.core.audio.SoundComponent;
import com.slimtrade.core.enums.CurrencyImage;

public class PriceThresholdData implements Comparable<PriceThresholdData> {

    public final CurrencyImage currencyType;
    public final int quantity;
    public final SoundComponent soundComponent;

    public PriceThresholdData(CurrencyImage currencyType, int quantity, SoundComponent soundComponent) {
        this.currencyType = currencyType;
        this.quantity = quantity;
        this.soundComponent = soundComponent;
    }

    @Override
    public int compareTo(PriceThresholdData o) {
        return quantity - o.quantity;
    }
}

