package github.zmilla93.gui.components.poe;

import github.zmilla93.core.enums.ResultStatus;
import github.zmilla93.gui.components.StyledLabel;

//FIXME : Move to theme module once that is updated
public class ResultLabel extends StyledLabel {

    private ResultStatus status;

    public ResultLabel() {
        this(ResultStatus.NEUTRAL, null);
    }

    public ResultLabel(ResultStatus status) {
        this(status, null);
    }

    public ResultLabel(String text) {
        this(ResultStatus.NEUTRAL, text);
    }

    public ResultLabel(ResultStatus status, String text) {
        this.status = status;
        setText(text);
        updateUI();
    }

    public void setText(ResultStatus status, String text) {
        this.status = status;
        setText(text);
        updateUI();
    }

    // FIXME : Implement
    @Override
    public void updateUI() {
        super.updateUI();
        if (status == null) return;
        setForeground(status.toColor());
    }

}
