package github.zmilla93.modules.theme.extensions.themes;

import github.zmilla93.modules.theme.ThemeColor;
import github.zmilla93.modules.theme.extensions.ThemeExtension;

import java.awt.*;

public class SolarizedLightExtension extends ThemeExtension {

    private static final Color RED = new Color(229, 88, 88);
    private static final Color GREEN = new Color(105, 201, 97);
    private static final Color ORANGE = new Color(220, 146, 72);

    public SolarizedLightExtension() {
        updateKey(ThemeColor.APPROVE, GREEN);
        updateKey(ThemeColor.DENY, RED);
        updateKey(ThemeColor.INDETERMINATE, ORANGE);
        updateKey(ThemeColor.INCOMING_MESSAGE, GREEN);
        updateKey(ThemeColor.OUTGOING_MESSAGE, RED);
        updateKey(ThemeColor.SCANNER_MESSAGE, ORANGE);
    }

}
