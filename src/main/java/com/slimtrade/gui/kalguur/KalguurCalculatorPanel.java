package com.slimtrade.gui.kalguur;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.GUIReferences;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.components.PlaceholderTextField;
import com.slimtrade.gui.listening.TextChangeListener;
import com.slimtrade.modules.saving.ISavable;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.util.ArrayList;

public class KalguurCalculatorPanel extends JPanel implements ISavable {

    private final JTextField inputField = new PlaceholderTextField("Ore", 4);
    private final JLabel outputLabel = new JLabel("0 ore");
    private final JPanel rowContainer = new JPanel();
    private final int ROW_LIMIT = 2;
    private final JDialog parentWindow;

    public KalguurCalculatorPanel(JDialog parentWindow) {
        this.parentWindow = parentWindow;
        setLayout(new BorderLayout());
        rowContainer.setLayout(new BoxLayout(rowContainer, BoxLayout.PAGE_AXIS));

        JPanel labelPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        gc.weightx = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        labelPanel.add(inputField, gc);
        gc.weightx = 0;
        gc.gridx++;
        gc.insets = new Insets(0, GUIReferences.HORIZONTAL_INSET_SMALL, 0, GUIReferences.HORIZONTAL_INSET_SMALL);
        labelPanel.add(outputLabel, gc);
        add(labelPanel, BorderLayout.CENTER);
        add(rowContainer, BorderLayout.SOUTH);

        addListeners();
        updateLabels();
    }

    public JTextField getInputField() {
        return inputField;
    }

    private int getInput() {
        try {
            return Integer.parseInt(inputField.getText());
        } catch (NumberFormatException ignore) {
            return -1;
        }
    }

    private void addListeners() {
        inputField.getDocument().addDocumentListener(new TextChangeListener() {
            @Override
            public void onTextChange(DocumentEvent e) {
                updateLabels();
            }
        });
        inputField.addActionListener(e1 -> {
            int value = getInput();
            if (value < 5) return;
            if (rowContainer.getComponentCount() == ROW_LIMIT) rowContainer.remove(0);
            KalguurQuantityRow row = new KalguurQuantityRow(parentWindow, this, rowContainer, value);
            rowContainer.add(row);
            clearInput();
            parentWindow.pack();
            SaveManager.appStateSaveFile.saveToDisk();
        });
    }

    private void updateLabels() {
        int input = getInput();
        int output = 0;
        if (input >= 5) output = input / 5;
        String outputText = output > 0 ? Integer.toString(output) : "?";
        outputLabel.setText("/5 = " + outputText);
        parentWindow.pack();
    }

    public void clearInput() {
        inputField.setText("");
        updateLabels();
    }

    @Override
    public void save() {
        ArrayList<Integer> values = new ArrayList<>();
        for (Component comp : rowContainer.getComponents()) {
            KalguurQuantityRow row = (KalguurQuantityRow) comp;
            values.add(row.getOreCount());
        }
        SaveManager.appStateSaveFile.data.kalguurQuantities = values;
    }

    @Override
    public void load() {
        rowContainer.removeAll();
        for (Integer i : SaveManager.appStateSaveFile.data.kalguurQuantities) {
            rowContainer.add(new KalguurQuantityRow(parentWindow, this, rowContainer, i));
        }
    }

}
