package github.zmilla93.gui.components;

import github.zmilla93.core.enums.SliderRange;

import javax.swing.*;

public class RangeSlider extends JSlider {

    public RangeSlider(SliderRange range) {
        super();
        setMinimum(range.MIN);
        setMaximum(range.MAX);
        setValue(range.START);
    }

}
