package com.slimtrade.gui.components;

import com.slimtrade.core.enums.SpinnerRange;

import javax.swing.*;

public class SpinnerRangeModel extends SpinnerNumberModel {

    public SpinnerRangeModel(SpinnerRange range) {
        setMinimum(range.MIN);
        setMaximum(range.MAX);
        setStepSize(range.STEP);
        setValue(range.START);
    }

}
