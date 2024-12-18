package github.zmilla93.modules.theme.extensions.themes;

import github.zmilla93.core.enums.ThemeColor;
import github.zmilla93.modules.theme.extensions.ColorOrKey;
import github.zmilla93.modules.theme.extensions.ColorVariant;
import github.zmilla93.modules.theme.extensions.ThemeExtension;

import java.awt.*;

public class SolarizedDarkExtension extends ThemeExtension {

    public SolarizedDarkExtension() {
        updateKey(ThemeColor.INDETERMINATE,
                new ColorVariant(new ColorOrKey(new Color(190, 96, 6))));
        updateKey(ThemeColor.SCANNER_MESSAGE,
                new ColorVariant(new ColorOrKey(new Color(190, 96, 6))));
    }

}
