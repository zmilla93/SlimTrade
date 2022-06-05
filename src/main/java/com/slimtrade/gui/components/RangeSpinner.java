package com.slimtrade.gui.components;

import com.slimtrade.core.enums.SpinnerRange;
import com.slimtrade.core.enums.SpinnerRangeFloat;

import javax.swing.*;

public class RangeSpinner extends JSpinner {

    public RangeSpinner(SpinnerRange range){
        SpinnerNumberModel model = new SpinnerNumberModel();
        model.setMinimum(range.MIN);
        model.setMaximum(range.MAX);
        model.setStepSize(range.STEP);
        model.setValue(range.START);
        setModel(model);
        setValue(range.START);
    }

    public RangeSpinner(SpinnerRangeFloat range){
        SpinnerNumberModel model = new SpinnerNumberModel();
        model.setMinimum(range.MIN);
        model.setMaximum(range.MAX);
        model.setStepSize(range.STEP);
        model.setValue(range.START);
        setModel(model);
        setValue(range.START);
    }

}
