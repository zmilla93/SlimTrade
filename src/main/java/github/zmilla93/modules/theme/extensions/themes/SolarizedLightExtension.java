package github.zmilla93.modules.theme.extensions.themes;

import github.zmilla93.core.enums.ThemeColor;
import github.zmilla93.modules.theme.extensions.ColorOrKey;
import github.zmilla93.modules.theme.extensions.ColorVariant;
import github.zmilla93.modules.theme.extensions.ThemeExtension;

import java.awt.*;

public class SolarizedLightExtension extends ThemeExtension {

    public SolarizedLightExtension() {
        updateKey(ThemeColor.APPROVE, new ColorVariant(
                new ColorOrKey(new Color(105, 201, 97))));
        updateKey(ThemeColor.DENY, new ColorVariant(
                new ColorOrKey(new Color(229, 88, 88))));
        updateKey(ThemeColor.INDETERMINATE, new ColorVariant(
                new ColorOrKey(new Color(220, 146, 72))));
        updateKey(ThemeColor.INCOMING_MESSAGE, new ColorVariant(
                new ColorOrKey(new Color(105, 201, 97))));
        updateKey(ThemeColor.OUTGOING_MESSAGE, new ColorVariant(
                new ColorOrKey(new Color(229, 88, 88))));
        updateKey(ThemeColor.SCANNER_MESSAGE, new ColorVariant(
                new ColorOrKey(new Color(220, 146, 72))));

    }

}
