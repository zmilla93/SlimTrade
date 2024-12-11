package github.zmilla93.gui.components;

import github.zmilla93.core.enums.SpinnerRange;

import javax.swing.*;
import javax.swing.text.NumberFormatter;

public class EditableRangeSpinner extends RangeSpinner {

    public EditableRangeSpinner(SpinnerRange range) {
        super(range);
        makeEditable();
    }

    private void makeEditable() {
        JFormattedTextField field = ((DefaultEditor) getEditor()).getTextField();
        field.setEditable(true);
        ((NumberFormatter) field.getFormatter()).setAllowsInvalid(false);
    }

}
