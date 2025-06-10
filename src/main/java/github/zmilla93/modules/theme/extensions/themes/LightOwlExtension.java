package github.zmilla93.modules.theme.extensions.themes;

import github.zmilla93.modules.theme.OLD_ThemeColor;
import github.zmilla93.modules.theme.extensions.ThemeExtension;

public class LightOwlExtension extends ThemeExtension {

    public LightOwlExtension() {
        updateKey(OLD_ThemeColor.APPROVE, "Objects.Yellow", "Actions.Blue");
        updateKey(OLD_ThemeColor.DENY, "Actions.Green", "Component.error.borderColor");
        updateKey(OLD_ThemeColor.INDETERMINATE, "Component.warning.borderColor");
        updateKey(OLD_ThemeColor.INCOMING_MESSAGE, "Objects.Yellow", "Actions.Blue");
        updateKey(OLD_ThemeColor.OUTGOING_MESSAGE, "Actions.Green", "Component.error.borderColor");
        updateKey(OLD_ThemeColor.SCANNER_MESSAGE, "Component.warning.borderColor");
    }

}
