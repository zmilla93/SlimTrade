package github.zmilla93.modules.theme.extensions.themes;

import github.zmilla93.core.enums.ThemeColor;
import github.zmilla93.modules.theme.extensions.ThemeExtension;

public class NightOwlExtension extends ThemeExtension {

    public NightOwlExtension() {
        updateKey(ThemeColor.APPROVE, "Objects.Yellow");
        updateKey(ThemeColor.DENY, "TitlePane.closeHoverBackground");
        updateKey(ThemeColor.INDETERMINATE, "Objects.Green");
        updateKey(ThemeColor.INCOMING_MESSAGE, "Objects.Yellow");
        updateKey(ThemeColor.OUTGOING_MESSAGE, "TitlePane.closeHoverBackground");
        updateKey(ThemeColor.SCANNER_MESSAGE, "Objects.Green");
    }

}
