package github.zmilla93.modules.theme.extensions.themes;

import github.zmilla93.core.enums.ThemeColor;
import github.zmilla93.modules.theme.extensions.ThemeExtension;

public class LightOwlExtension extends ThemeExtension {

    public LightOwlExtension() {
        updateKey(ThemeColor.APPROVE, "Objects.Yellow", "Actions.Blue");
        updateKey(ThemeColor.DENY, "Actions.Green", "Component.error.borderColor");
        updateKey(ThemeColor.INDETERMINATE, "Component.warning.borderColor");
        updateKey(ThemeColor.INCOMING_MESSAGE, "Objects.Yellow", "Actions.Blue");
        updateKey(ThemeColor.OUTGOING_MESSAGE, "Actions.Green", "Component.error.borderColor");
        updateKey(ThemeColor.SCANNER_MESSAGE, "Component.warning.borderColor");
    }

}
