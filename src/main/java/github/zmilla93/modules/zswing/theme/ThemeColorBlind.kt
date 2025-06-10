package github.zmilla93.modules.zswing.theme

import github.zmilla93.modules.zswing.uiproperty.IColor
import java.awt.Color
import javax.swing.UIManager

enum class ThemeColorBlind(val colorKey: String, val colorKeyCB: String) : IColor {

    GREEN("Actions.Green", "Actions.Blue"),
    RED("Actions.Red", "Objects.Pink"),
    YELLOW("Actions.Yellow", "Actions.Yellow"),
    ;

    override fun color(): Color {
        if (colorBlindMode) return UIManager.getColor(colorKeyCB)
        else (return UIManager.getColor(colorKey))
    }

    companion object {
        var colorBlindMode = false
    }

}