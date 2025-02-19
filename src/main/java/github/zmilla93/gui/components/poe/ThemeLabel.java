package github.zmilla93.gui.components.poe;

import github.zmilla93.gui.components.StyledLabel;
import github.zmilla93.modules.theme.ThemeColor;

//FIXME : Move to theme module once that is updated
public class ThemeLabel extends StyledLabel {

    private ThemeColor status;

    public ThemeLabel() {
        this(ThemeColor.NEUTRAL, null);
    }

    public ThemeLabel(ThemeColor status) {
        this(status, null);
    }

    public ThemeLabel(String text) {
        this(ThemeColor.NEUTRAL, text);
    }

    public ThemeLabel(ThemeColor status, String text) {
        this.status = status;
        setText(text);
        updateUI();
    }

    public void setText(ThemeColor status, String text) {
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
