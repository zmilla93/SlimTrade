package github.zmilla93.modules.theme.extensions.themes;

import github.zmilla93.core.enums.ThemeColor;
import github.zmilla93.modules.theme.extensions.ColorOrKey;
import github.zmilla93.modules.theme.extensions.ColorVariant;
import github.zmilla93.modules.theme.extensions.ThemeExtension;

public class LightOwlExtension extends ThemeExtension {

    public LightOwlExtension() {
        updateKey(ThemeColor.APPROVE, new ColorVariant(
                new ColorOrKey("Objects.Yellow"),
                new ColorOrKey("Actions.Blue")));
        updateKey(ThemeColor.DENY, new ColorVariant(
                new ColorOrKey("Actions.Green"),
                new ColorOrKey("Component.error.borderColor")));
        updateKey(ThemeColor.INDETERMINATE, new ColorVariant(new ColorOrKey("Component.warning.borderColor")));
        updateKey(ThemeColor.INCOMING_MESSAGE, new ColorVariant(
                new ColorOrKey("Objects.Yellow"),
                new ColorOrKey("Actions.Blue")));
        updateKey(ThemeColor.OUTGOING_MESSAGE, new ColorVariant(
                new ColorOrKey("Actions.Green"),
                new ColorOrKey("Component.error.borderColor")));
        updateKey(ThemeColor.SCANNER_MESSAGE, new ColorVariant(new ColorOrKey("Component.warning.borderColor")));
    }

}
