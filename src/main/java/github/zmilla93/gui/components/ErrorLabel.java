package github.zmilla93.gui.components;

import github.zmilla93.gui.components.poe.ThemeLabel;
import github.zmilla93.modules.theme.OLD_ThemeColor;

public class ErrorLabel extends ThemeLabel {

    public ErrorLabel() {
        super(OLD_ThemeColor.DENY);
        bold();
    }

    public ErrorLabel(String text) {
        super(OLD_ThemeColor.DENY, text);
        bold();
    }

}
