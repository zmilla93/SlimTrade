package com.slimtrade.gui.components.slimtrade;

import com.slimtrade.core.enums.ResultStatus;
import com.slimtrade.gui.components.StyledLabel;

//FIXME : Move to theme module once that is updated
public class ResultLabel extends StyledLabel {

    public ResultLabel(ResultStatus status) {
        setForeground(status.toColor());
    }

    public ResultLabel(ResultStatus status, String text) {
        setForeground(status.toColor());
        setText(text);
    }

    // FIXME : Implement
    @Override
    public void updateUI() {
        super.updateUI();
    }

}
