package github.zmilla93.gui.donate

import github.zmilla93.core.utility.ResourceUtil

/**
 * Handles reading supporter info from csvs.
 */
class Supporters {

    companion object {

        val patreon = ArrayList<PatreonSupporter>()
        val paypal = ArrayList<PayPalSupporter>()

        init {
            /** Read Patreon */
            ResourceUtil.read("/csv/patreon.csv") {
                if (it.isEmpty()) return@read
                val values = it.trim().split(",")
                PatreonSupporter(
                    values[0],
                    PatreonTier.fromAmount(values[1].toInt()),
                    PayPalTier.fromAmount(values[2].toInt())
                )
                patreon += PatreonSupporter.fromCSV(it)
            }
            // FIXME : Should presort csv
            patreon.sort()
            /** Read PayPal */
            ResourceUtil.read("/csv/paypal.csv") {
                if (it.isEmpty()) return@read
                val values = it.trim().split(",")
                paypal += PayPalSupporter(values[0], PayPalTier.fromAmount(values[1].toInt()))
            }
        }

        /** Returns the sum of all active patrons. */
        fun getCurrentPatreonCount(): Int {
            var amount = 0
            patreon.forEach { amount += it.tier.amount }
            return amount
        }

    }

}