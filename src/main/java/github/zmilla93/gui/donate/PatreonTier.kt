package github.zmilla93.gui.donate

import java.awt.Color

enum class PatreonTier(val amount: Int, val amountText: String, val background: Color, val foreground: Color) {

    ZERO(0, "Inactive", Color.BLACK, Color.GRAY),
    ONE(1, "$1", Color(20, 0, 40), Color(51, 22, 240)),
    THREE(3, "$3", Color(51, 37, 0), Color(255, 188, 5)),
    FIVE(5, "$5", Color(175, 96, 37), Color(255, 255, 255)),
    TEN(10, "$10", Color(255, 255, 255), Color(255, 0, 0))
    ;

    companion object {

        const val HEADER_TEXT = "Spare some currency?"
        const val INFO_TEXT =
            "Software development is time consuming. An occasional dollar adds up when everyone does it."
        const val PATRON_GOAL = 500
        val GOAL_TEXT = "Patreon Goal - ${Supporters.getCurrentPatreonCount()}/$PATRON_GOAL"

        fun fromAmount(amount: Int): PatreonTier {
            entries.reversed().forEach { tier ->
                if (amount >= tier.amount) return tier
            }
            return ZERO
        }

    }

}