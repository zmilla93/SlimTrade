package com.slimtrade.gui.options.ignore;

import com.slimtrade.App;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.buttons.BasicButton;
import com.slimtrade.gui.components.AddRemovePanel;
import com.slimtrade.gui.custom.CustomCombo;
import com.slimtrade.gui.custom.CustomLabel;
import com.slimtrade.gui.custom.CustomSpinner;
import com.slimtrade.gui.custom.CustomTextField;
import com.slimtrade.gui.enums.MatchType;
import com.slimtrade.gui.options.ISaveable;
import com.slimtrade.gui.panels.BufferPanel;
import com.slimtrade.gui.panels.ContainerPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ItemIgnorePanel extends ContainerPanel implements ISaveable {

    private static final long serialVersionUID = 1L;

    private AddRemovePanel addRemovePanel = new AddRemovePanel();
    private JTextField itemText = new CustomTextField(30);
    private JComboBox<MatchType> typeCombo = new CustomCombo<>();
    private SpinnerModel spinnerModel = new SpinnerNumberModel(60, 0, 300, 10);
    private JSpinner timerSpinner = new CustomSpinner(spinnerModel);
    private JButton ignoreButton = new BasicButton("Ignore Item");

    private final int MAX_IGNORE_COUNT = 40;

    public ItemIgnorePanel() {
        // TODO : remove this assignment and expose through method
        FrameManager.ignoreItemAddRemovePanel = addRemovePanel;

        JPanel entryPanel = new JPanel(new GridBagLayout());
        entryPanel.setOpaque(false);

        JLabel itemLabel = new CustomLabel("Item Name");
        JLabel typeLabel = new CustomLabel("Match Type");
        JLabel timerLabel = new CustomLabel("Minutes");
        JLabel info1 = new CustomLabel("Set minutes to 0 to ignore items indefinitely.");
        JLabel info2 = new CustomLabel("Right click on the item name of an incoming trade to quickly ignore that item for 1 hour.");

        for (MatchType type : MatchType.values()) {
            typeCombo.addItem(type);
        }

        container.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;

        gc.insets.left = 10;
        gc.insets.right = 10;

        // Entry Panel
        gc.gridx = 1;
        entryPanel.add(new BufferPanel(200, 0), gc);
        gc.gridy++;

        entryPanel.add(itemLabel, gc);
        gc.gridx++;
        entryPanel.add(typeLabel, gc);
        gc.gridx++;
        entryPanel.add(timerLabel, gc);
        gc.gridx = 0;
        gc.gridy++;

        entryPanel.add(ignoreButton, gc);
        gc.gridx++;
        entryPanel.add(itemText, gc);
        gc.gridx++;
        entryPanel.add(typeCombo, gc);
        gc.gridx++;
        entryPanel.add(timerSpinner, gc);
        gc.gridx = 0;
        gc.gridy++;

        // Reset gc
        gc.fill = GridBagConstraints.NONE;
//        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridwidth = 1;
        gc.insets.left = 0;
        gc.insets.right = 0;

        // Container
        container.add(entryPanel, gc);
        gc.gridy++;
        container.add(info1, gc);
        gc.gridy++;
        container.add(info2, gc);
        gc.gridy++;
        gc.insets.top = 15;
        container.add(addRemovePanel, gc);
        gc.insets.top = 0;
        gc.gridy++;

        FrameManager.itemIgnorePanel = this;

        ignoreButton.addActionListener(e -> {
            String text = itemText.getText().trim();
            if (text.matches("")) {
                return;
            }
            int i = 0;
            for (Component c : addRemovePanel.getComponents()) {
                if (c instanceof IgnoreRow) {
                    IgnoreRow row = (IgnoreRow) c;
                    if (!row.isToBeDeleted() && text.toLowerCase().matches(row.getIgnoreData().getItemName().toLowerCase())) {
                        return;
                    }
                    if (row.isVisible()) {
                        i++;
                    }
                }
            }
            if (i >= MAX_IGNORE_COUNT) {
                return;
            }
            i = (int) timerSpinner.getValue();
            itemText.setText("");
            addRemovePanel.addRemovablePanel(new IgnoreRow(new IgnoreData(text, (MatchType) typeCombo.getSelectedItem(), i), addRemovePanel));
        });

        load();

    }

    @Override
    public void save() {
        addRemovePanel.clearHiddenPanels();
        App.saveManager.saveFile.ignoreData.clear();
        for (Component c : addRemovePanel.getComponents()) {
            if (c instanceof IgnoreRow) {
                IgnoreRow row = (IgnoreRow) c;
                IgnoreData rowData = row.getIgnoreData();
                if (rowData.getRemainingTime() > 0 || rowData.indefinite) {
                    App.saveManager.saveFile.ignoreData.add(rowData);
                }
            }
        }
        App.chatParser.setWhisperIgnoreTerms(App.saveManager.saveFile.ignoreData);
    }

    @Override
    public void load() {
        ArrayList<IgnoreData> fullData = new ArrayList<>();
        addRemovePanel.removeAll();
        for (IgnoreData data : App.saveManager.saveFile.ignoreData) {
            if (data.getRemainingTime() > -0 || data.indefinite) {
                fullData.add(data);
                addRemovePanel.addRemovablePanel(new IgnoreRow(data, addRemovePanel));
            }
        }
//        addRemovePanel.saveChanges();
        App.chatParser.setWhisperIgnoreTerms(fullData);
    }

}
