package github.zmilla93.gui.donate

data class PatreonSupporter(val name: String, val tier: PatreonTier, val paypalTier: PayPalTier) :
    Comparable<PatreonSupporter> {

    companion object {

        fun fromCSV(csv: String): PatreonSupporter {
            val values = csv.trim().split(",")
            return PatreonSupporter(
                values[0],
                PatreonTier.fromAmount(values[1].toInt()),
                PayPalTier.fromAmount(values[2].toInt())
            )
        }

    }

    override fun compareTo(other: PatreonSupporter): Int {
        return name.compareTo(other.name)
    }

}