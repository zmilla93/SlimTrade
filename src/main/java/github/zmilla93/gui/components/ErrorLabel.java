package github.zmilla93.gui.components;

import github.zmilla93.core.enums.ThemeColor;
import github.zmilla93.gui.components.poe.ThemeLabel;

public class ErrorLabel extends ThemeLabel {

    public ErrorLabel() {
        super(ThemeColor.DENY);
        bold();
    }

    public ErrorLabel(String text) {
        super(ThemeColor.DENY, text);
        bold();
    }

}
