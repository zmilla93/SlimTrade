package github.zmilla93.modules.zswing.uiproperty

import java.awt.Color

/**
 * Interface for classes that resolve a color dynamically based on the current theme.
 */
fun interface IColor {

    fun color(): Color

}