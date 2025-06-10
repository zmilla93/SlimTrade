package github.zmilla93.gui.components.poe;

import github.zmilla93.gui.components.StyledLabel;
import github.zmilla93.modules.theme.OLD_ThemeColor;

//FIXME : Move to theme module once that is updated
public class ThemeLabel extends StyledLabel {

    private OLD_ThemeColor status;

    public ThemeLabel() {
        this(OLD_ThemeColor.NEUTRAL, null);
    }

    public ThemeLabel(OLD_ThemeColor status) {
        this(status, null);
    }

    public ThemeLabel(String text) {
        this(OLD_ThemeColor.NEUTRAL, text);
    }

    public ThemeLabel(OLD_ThemeColor status, String text) {
        this.status = status;
        setText(text);
        updateUI();
    }

    public void setText(OLD_ThemeColor status, String text) {
        this.status = status;
        setText(text);
        updateUI();
    }

    @Override
    public void updateUI() {
        super.updateUI();
        if (status == null) return;
        setForeground(status.current());
    }

}
