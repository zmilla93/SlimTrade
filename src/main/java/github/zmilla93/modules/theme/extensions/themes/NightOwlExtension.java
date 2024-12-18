package github.zmilla93.modules.theme.extensions.themes;

import github.zmilla93.core.enums.ThemeColor;
import github.zmilla93.modules.theme.extensions.ColorOrKey;
import github.zmilla93.modules.theme.extensions.ColorVariant;
import github.zmilla93.modules.theme.extensions.ThemeExtension;

public class NightOwlExtension extends ThemeExtension {

    public NightOwlExtension() {
        updateKey(ThemeColor.APPROVE, new ColorVariant(new ColorOrKey("Objects.Yellow")));
        updateKey(ThemeColor.DENY, new ColorVariant(new ColorOrKey("TitlePane.closeHoverBackground")));
        updateKey(ThemeColor.INDETERMINATE, new ColorVariant(new ColorOrKey("Objects.Green")));
        updateKey(ThemeColor.INCOMING_MESSAGE, new ColorVariant(new ColorOrKey("Objects.Yellow")));
        updateKey(ThemeColor.OUTGOING_MESSAGE, new ColorVariant(new ColorOrKey("TitlePane.closeHoverBackground")));
        updateKey(ThemeColor.SCANNER_MESSAGE, new ColorVariant(new ColorOrKey("Objects.Green")));
    }

}
