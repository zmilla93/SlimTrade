package github.zmilla93.modules.theme.extensions.themes;

import github.zmilla93.modules.theme.OLD_ThemeColor;
import github.zmilla93.modules.theme.extensions.ThemeExtension;

public class NightOwlExtension extends ThemeExtension {

    public NightOwlExtension() {
        updateKey(OLD_ThemeColor.APPROVE, "Objects.Yellow");
        updateKey(OLD_ThemeColor.DENY, "TitlePane.closeHoverBackground");
        updateKey(OLD_ThemeColor.INDETERMINATE, "Objects.Green");
        updateKey(OLD_ThemeColor.INCOMING_MESSAGE, "Objects.Yellow");
        updateKey(OLD_ThemeColor.OUTGOING_MESSAGE, "TitlePane.closeHoverBackground");
        updateKey(OLD_ThemeColor.SCANNER_MESSAGE, "Objects.Green");
    }

}
