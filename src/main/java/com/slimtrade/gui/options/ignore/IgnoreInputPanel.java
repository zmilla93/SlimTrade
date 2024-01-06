package com.slimtrade.gui.options.ignore;

import com.slimtrade.core.data.IgnoreItemData;
import com.slimtrade.core.enums.MatchType;
import com.slimtrade.core.utility.ZUtil;

import javax.swing.*;
import java.awt.*;

public class IgnoreInputPanel extends JPanel {

    private final JButton ignoreButton = new JButton("Ignore Item");
    private final JTextField itemNameInput = new JTextField(20);
    private final JComboBox<MatchType> matchTypeCombo = new JComboBox<>();
    private final JSpinner timeSpinner = new JSpinner();

    public IgnoreInputPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();

        for (MatchType matchType : MatchType.values()) matchTypeCombo.addItem(matchType);
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel();
        spinnerModel.setMinimum(0);
        spinnerModel.setMaximum(300);
        spinnerModel.setStepSize(10);
        spinnerModel.setValue(60);
        timeSpinner.setModel(spinnerModel);
        ((JSpinner.DefaultEditor) timeSpinner.getEditor()).getTextField().setEditable(false);

        add(new JLabel("Item Name"), gc);
        gc.gridx++;
        add(new JLabel("Match"), gc);
        gc.gridx++;
        add(new JLabel("Minutes"), gc);
        gc.gridx = 0;
        gc.gridy++;
        add(itemNameInput, gc);
        gc.gridx++;
        add(matchTypeCombo, gc);
        gc.gridx++;
        add(timeSpinner, gc);
        gc.gridx++;
        add(ignoreButton, gc);
        ignoreButton.addActionListener(e -> itemNameInput.setText(""));
    }

    public void clear() {
        itemNameInput.setText("");
    }

    public JButton getIgnoreButton() {
        return ignoreButton;
    }

    public IgnoreItemData getIgnoreItem() {
        String name = itemNameInput.getText();
        MatchType matchType = (MatchType) matchTypeCombo.getSelectedItem();
        int duration = (int) timeSpinner.getValue();
        return new IgnoreItemData(name, matchType, duration);
    }

    // FIXME : Setting using expiration time is technically incorrect, need to change is this function gets used more
    public void setIgnoreItem(IgnoreItemData item) {
        itemNameInput.setText(item.itemName);
        matchTypeCombo.setSelectedItem(item.matchType);
        timeSpinner.setValue(item.initialDuration);
    }

}
