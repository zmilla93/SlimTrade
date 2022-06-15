package com.slimtrade.core.data;

import com.slimtrade.core.audio.SoundComponent;
import com.slimtrade.core.enums.CommonCurrency;

public class PriceThresholdData {

    public final CommonCurrency currencyType;
    public final int quantity;
    public final SoundComponent soundComponent;

    public PriceThresholdData(CommonCurrency currencyType, int quantity, SoundComponent soundComponent) {
        this.currencyType = currencyType;
        this.quantity = quantity;
        this.soundComponent = soundComponent;
    }

}
