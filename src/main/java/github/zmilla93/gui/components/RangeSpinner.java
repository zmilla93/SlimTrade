package github.zmilla93.gui.components;

import github.zmilla93.core.enums.SpinnerRange;
import github.zmilla93.core.enums.SpinnerRangeFloat;

import javax.swing.*;

public class RangeSpinner extends JSpinner {

    public RangeSpinner(SpinnerRange range) {
        SpinnerNumberModel model = new SpinnerNumberModel();
        model.setMinimum(range.MIN);
        model.setMaximum(range.MAX);
        model.setStepSize(range.STEP);
        model.setValue(range.START);
        setModel(model);
        ((DefaultEditor) getEditor()).getTextField().setEditable(false);
    }

    public RangeSpinner(SpinnerRangeFloat range) {
        SpinnerNumberModel model = new SpinnerNumberModel();
        model.setMinimum(range.MIN);
        model.setMaximum(range.MAX);
        model.setStepSize(range.STEP);
        model.setValue(range.START);
        setModel(model);
        ((DefaultEditor) getEditor()).getTextField().setEditable(false);
    }

}
