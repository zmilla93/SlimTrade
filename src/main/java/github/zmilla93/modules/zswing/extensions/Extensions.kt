package github.zmilla93.modules.zswing.extensions

import java.awt.Color

object Extensions {

    fun Color.noAlpha(): Color {
        return Color(red, green, blue, 255)
    }

}