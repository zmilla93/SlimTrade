package github.zmilla93.gui.donate

enum class PayPalTier(val amount: Int, val image: String?) {

    ZERO(0, null),
    ONE(1, "/poe1/icons/Orb of Alchemy.png"),
    FIVE(5, "/poe1/icons/Chaos Orb.png"),
    TEN(10, "/poe1/icons/Exalted Orb.png"),
    TWENTY(20, "/poe1/icons/Divine Orb.png"),
    FIFTY(50, "/poe1/icons/Warlord's Exalted Orb.png")
    ;

    companion object {

        fun fromAmount(amount: Int): PayPalTier {
            entries.reversed().forEach { tier ->
                if (amount >= tier.amount) return tier
            }
            return ONE
        }

    }

}