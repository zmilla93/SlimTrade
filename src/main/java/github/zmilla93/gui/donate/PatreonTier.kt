package github.zmilla93.gui.donate

import java.awt.Color

enum class PatreonTier(val amount: String, val background: Color, val foreground: Color) {

    ZERO("Inactive", Color.BLACK, Color.GRAY),
    ONE("$1", Color(20, 0, 40), Color(51, 22, 240)),
    THREE("$3", Color(51, 37, 0), Color(255, 188, 5)),
    FIVE("$5", Color(175, 96, 37), Color(255, 255, 255)),
    TEN("$10", Color(255, 255, 255), Color(255, 0, 0))

}