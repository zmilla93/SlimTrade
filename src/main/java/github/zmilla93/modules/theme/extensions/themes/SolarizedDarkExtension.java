package github.zmilla93.modules.theme.extensions.themes;

import github.zmilla93.core.enums.ThemeColor;
import github.zmilla93.modules.theme.extensions.ThemeExtension;

import java.awt.*;

public class SolarizedDarkExtension extends ThemeExtension {

    private static final Color ORANGE = new Color(236, 133, 47);

    public SolarizedDarkExtension() {
        updateKey(ThemeColor.INDETERMINATE, ORANGE);
        updateKey(ThemeColor.SCANNER_MESSAGE, ORANGE);
    }

}
