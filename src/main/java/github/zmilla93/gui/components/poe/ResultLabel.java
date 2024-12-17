package github.zmilla93.gui.components.poe;

import github.zmilla93.core.enums.ThemeColor;
import github.zmilla93.gui.components.StyledLabel;

//FIXME : Move to theme module once that is updated
public class ResultLabel extends StyledLabel {

    private ThemeColor status;

    public ResultLabel() {
        this(ThemeColor.NEUTRAL, null);
    }

    public ResultLabel(ThemeColor status) {
        this(status, null);
    }

    public ResultLabel(String text) {
        this(ThemeColor.NEUTRAL, text);
    }

    public ResultLabel(ThemeColor status, String text) {
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
