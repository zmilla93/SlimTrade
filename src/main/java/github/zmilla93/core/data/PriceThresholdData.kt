package github.zmilla93.core.data

import github.zmilla93.core.audio.SoundComponent
import github.zmilla93.core.enums.CurrencyType

class PriceThresholdData(
    @JvmField val currencyType: CurrencyType?,
    @JvmField val quantity: Int,
    @JvmField val soundComponent: SoundComponent
) : Comparable<PriceThresholdData> {

    /** GSON Constructor  */
    constructor() : this(null, 0, SoundComponent())

    override fun compareTo(other: PriceThresholdData): Int {
        return quantity - other.quantity
    }

}

