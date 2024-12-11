package github.zmilla93.gui.options;

import javax.swing.*;


public class OptionListSelectionModel extends DefaultListSelectionModel {

    private final OptionListPanel[] panels;

    public OptionListSelectionModel(OptionListPanel[] panels) {
        this.panels = panels;
        setSelectionMode(SINGLE_SELECTION);
    }

    // Don't allow separators to be selectable.
    @Override
    public void setSelectionInterval(int index0, int index1) {
        OptionListPanel obj = panels[index0];
        if (obj.isSeparator) return;
        super.setSelectionInterval(index0, index1);
    }

}
