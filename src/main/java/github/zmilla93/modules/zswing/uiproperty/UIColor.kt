package github.zmilla93.modules.zswing.uiproperty

import java.awt.Color
import javax.swing.UIManager

/**
 * Common UIManager keys.
 */
enum class UIColor(val key: String) : IColor {

    // Panel
    PANEL_BACKGROUND("Panel.background"),

    // Label
    LABEL_FOREGROUND("Label.foreground"),
    LABEL_BACKGROUND("Label.background"),

    // Button
    BUTTON_BORDER("Button.startBorderColor"),
    BUTTON_TEXT_DISABLED("Button.disabledText"),
    BUTTON_BACKGROUND("Button.background"),
    BUTTON_FOREGROUND("Button.foreground"),
    BUTTON_RADIO_BACKGROUND("RadioButton.background"),
    BUTTON_DISABLED_BACKGROUND("Button.disabledBackground"),

    // Text Area
    TEXT_AREA_BACKGROUND("TextArea.background")
    ;

    override fun color(): Color {
        return UIManager.getColor(key)
    }

}