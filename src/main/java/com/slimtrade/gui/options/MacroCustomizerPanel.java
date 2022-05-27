package com.slimtrade.gui.options;

import com.slimtrade.core.enums.ButtonRow;
import com.slimtrade.core.enums.CustomIcon;
import com.slimtrade.core.enums.DefaultIcon;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.ColorManager;
import com.slimtrade.core.utility.MacroButton;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.components.AddRemoveContainer;
import com.slimtrade.gui.components.AddRemovePanel;
import com.slimtrade.gui.components.CustomCombo;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class MacroCustomizerPanel extends AddRemovePanel {

    private int textFieldWidth = 20;

    // Macro Button
    CustomCombo<ImageIcon> iconCombo = new CustomCombo();
    JTextField buttonText = new JTextField();

    JTextField lmbInput = new JTextField(textFieldWidth);
    JTextField rmbInput = new JTextField(textFieldWidth);
    JCheckBox closeCheckbox = new JCheckBox();
    JComboBox<ButtonRow> rowCombo = new JComboBox<>();
    JComboBox<MacroButton.MacroButtonType> buttonType = new JComboBox<>();

    // Internal
    JPanel parent;
    private CardLayout displayLayout = new CardLayout();
    private JPanel displayPanel = new JPanel(displayLayout);
    JButton shiftUpButton = new IconButton(DefaultIcon.ARROW_UP.path);
    JButton shiftDownButton = new IconButton(DefaultIcon.ARROW_DOWN.path);
    JButton deleteButton = new IconButton(DefaultIcon.CLOSE.path);
    private GridBagConstraints gc = ZUtil.getGC();

    public MacroCustomizerPanel(AddRemoveContainer parent) {
        super(parent);
        this.parent = parent;
        setLayout(new GridBagLayout());

        // Labels
        JLabel lmbLabel = new JLabel("LMB");
        JLabel rmbLabel = new JLabel("RMB");
        JLabel closeLabel = new JLabel("Close");
        ColorManager.addStickyCombo(iconCombo);
        buttonText.setText("~");

        // Card Type Panel

        displayPanel.add(iconCombo, MacroButton.MacroButtonType.ICON.toString());
        displayPanel.add(buttonText, MacroButton.MacroButtonType.TEXT.toString());

        gc.insets = new Insets(0, 5, 0, 5);

        // Add items to combos
        for (ButtonRow row : ButtonRow.values())
            rowCombo.addItem(row);
        for (CustomIcon icon : CustomIcon.values())
            iconCombo.addItem(icon.getColorIcon(UIManager.getColor("Label.foreground"), 18));
        for (MacroButton.MacroButtonType type : MacroButton.MacroButtonType.values())
            buttonType.addItem(type);

        // Shift Buttons
        add(shiftUpButton, gc);
        gc.gridy++;
        add(shiftDownButton, gc);
        gc.gridx++;
        gc.gridy = 0;

        //Labels
        add(lmbLabel, gc);
        gc.gridy++;
        add(rmbLabel, gc);
        gc.gridx++;
        gc.gridy = 0;

        // Response Input
        add(lmbInput, gc);
        gc.gridy++;
        add(rmbInput, gc);
        gc.gridx++;
        gc.gridy = 0;

        // Icon  + Row
//        gc.fill = GridBagConstraints.HORIZONTAL;
//        gc.weightx = 1;
//        add(iconCombo, gc);
//        gc.gridy++;
//        add(rowCombo, gc);
//        gc.gridx++;
//        gc.gridy = 0;

        // Icon  + Row

        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;
        add(buttonType, gc);
        gc.gridy++;
        add(displayPanel, gc);
        gc.gridx++;
        gc.gridy = 0;

        add(rowCombo);
        gc.gridx++;
        gc.gridy = 0;
//        gc.gridx++;
//        gc.gridy = 0;

        // Close
        gc.fill = GridBagConstraints.NONE;
        gc.weightx = 1;
        add(closeLabel, gc);
        gc.gridy++;
        add(closeCheckbox, gc);
        gc.gridx++;
        gc.gridy = 0;

        // Delete Button
        gc.fill = GridBagConstraints.HORIZONTAL;
        add(deleteButton);

        updateUI();
        addListeners();
    }

    private void addListeners() {
        JPanel self = this;
        shiftUpButton.addActionListener(e -> shiftUp(shiftUpButton));
        shiftDownButton.addActionListener(e -> shiftDown(shiftDownButton));
        deleteButton.addActionListener(e -> {
            ColorManager.removeStickyCombo(iconCombo);
            parent.remove(self);
            parent.revalidate();
        });
        buttonType.addActionListener(e -> {
            if (buttonType.getSelectedItem() == null) return;
            displayLayout.show(displayPanel, Objects.requireNonNull(buttonType.getSelectedItem()).toString());
        });
    }

    public MacroButton getMacroButton() {
        MacroButton button = new MacroButton();
        button.lmbResponse = lmbInput.getText();
        button.rmbResponse = rmbInput.getText();
        button.row = (ButtonRow) rowCombo.getSelectedItem();
        button.buttonType = (MacroButton.MacroButtonType) buttonType.getSelectedItem();
        button.text = buttonText.getText();
        button.close = closeCheckbox.isSelected();
        int index = iconCombo.getSelectedIndex();
        if (index == -1) index = 0;
        button.icon = CustomIcon.values()[index];
        return button;
    }

    public void setMacro(MacroButton macro) {
        lmbInput.setText(macro.lmbResponse);
        rmbInput.setText(macro.rmbResponse);
        rowCombo.setSelectedItem(macro.row);
        buttonType.setSelectedItem(macro.buttonType);
        buttonText.setText(macro.text);
        iconCombo.setSelectedIndex(macro.icon.ordinal());
        closeCheckbox.setSelected(macro.close);
    }

    @Override
    public void updateUI() {
        super.updateUI();
        if (iconCombo != null) {
            int selectedIndex = iconCombo.getSelectedIndex();
            iconCombo.removeAllItems();
            for (CustomIcon icon : CustomIcon.values()) {
                iconCombo.addItem(icon.getColorIcon(UIManager.getColor("Label.foreground"), SaveManager.settingsSaveFile.data.iconSize));
            }
            iconCombo.setSelectedIndex(selectedIndex);
        }
        setBorder(BorderFactory.createLineBorder(UIManager.getColor("Separator.foreground")));
    }


}
