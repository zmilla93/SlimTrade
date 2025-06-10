package github.zmilla93.modules.zswing.theme

import github.zmilla93.modules.zswing.uiproperty.IColor
import java.awt.Color
import javax.swing.UIManager

enum class ThemeColor(val key: String) : IColor {


    // FIXME @important : Action Colors
//    Actions
//    GREEN,
//    RED,
//    YELLOW,
//    BLUE,
//    GRAY,
//    GRAY_INLINE,

    //    OBJECTS
//    Black
//    Green
//    Grey
//    Purple
//    Pink
//    YelowDark
//    GreenAndroid
//    Blue
//    RedStatus
//    Yellow
//    Red(Orange, useRedStatus)
    GREY("Objects.Grey"),
    BLACK("Objects.BlackText"),
    GREEN("Objects.Green"),
    GREENA("Actions.Green"),
    PURPLE("Objects.Purple"),
    PINK("Objects.Pink"),
    RED("Objects.Red"),
    REDA("Actions.Red"),
    BLUE("Objects.Blue"),
    BLUEA("Actions.Blue"),
//    FIXME @IMPORTANT : Red/Orange/Yellow are slightly inconsistent between themes, should make themes define it explicitly
//    YELLOW("Objects."),
//    ORANGE("Objects."),
//    RED("Objects."),
    ;

    override fun color(): Color {
        return UIManager.getColor(key) as Color
    }

}