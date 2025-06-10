package github.zmilla93.modules.theme.extensions.themes;

import github.zmilla93.modules.theme.OLD_ThemeColor;
import github.zmilla93.modules.theme.extensions.ThemeExtension;

import java.awt.*;

public class SolarizedLightExtension extends ThemeExtension {

    private static final Color RED = new Color(229, 88, 88);
    private static final Color GREEN = new Color(105, 201, 97);
    private static final Color ORANGE = new Color(220, 146, 72);

    public SolarizedLightExtension() {
        updateKey(OLD_ThemeColor.APPROVE, GREEN);
        updateKey(OLD_ThemeColor.DENY, RED);
        updateKey(OLD_ThemeColor.INDETERMINATE, ORANGE);
        updateKey(OLD_ThemeColor.INCOMING_MESSAGE, GREEN);
        updateKey(OLD_ThemeColor.OUTGOING_MESSAGE, RED);
        updateKey(OLD_ThemeColor.SCANNER_MESSAGE, ORANGE);
    }

}
