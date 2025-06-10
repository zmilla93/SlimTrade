package github.zmilla93.modules.theme.extensions.themes;

import github.zmilla93.modules.theme.OLD_ThemeColor;
import github.zmilla93.modules.theme.extensions.ThemeExtension;

import java.awt.*;

public class SolarizedDarkExtension extends ThemeExtension {

    private static final Color ORANGE = new Color(236, 133, 47);

    public SolarizedDarkExtension() {
        updateKey(OLD_ThemeColor.INDETERMINATE, ORANGE);
        updateKey(OLD_ThemeColor.SCANNER_MESSAGE, ORANGE);
    }

}
