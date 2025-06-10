package github.zmilla93.gui.donate

import java.awt.Color

enum class PatreonTier(val amount: String, val background: Color, val foreground: Color) {

    ZERO("None", Color.BLACK, Color.GRAY),
    ONE("$1", Color.BLACK, Color.WHITE),
    THREE("$3", Color.BLACK, Color.BLUE),
    FIVE("$5", Color(51, 37, 0), Color(255, 188, 5)),
    TEN("$10", Color.WHITE, Color.RED)

}