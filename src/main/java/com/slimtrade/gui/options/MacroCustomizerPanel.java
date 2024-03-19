package com.slimtrade.gui.options;

import com.slimtrade.core.enums.ButtonRow;
import com.slimtrade.core.enums.CustomIcon;
import com.slimtrade.core.enums.DefaultIcon;
import com.slimtrade.core.enums.MacroButtonType;
import com.slimtrade.core.utility.MacroButton;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.components.*;
import com.slimtrade.modules.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class MacroCustomizerPanel extends AddRemovePanel {

    // Macro Button
    private final JComboBox<ImageIcon> iconCombo = new LimitCombo<>();
    private final JTextField buttonText = new JTextField();

    private final int TEXT_FIELD_COLUMNS = 20;
    private final JTextField lmbInput = new JTextField(TEXT_FIELD_COLUMNS);
    private final JTextField rmbInput = new JTextField(TEXT_FIELD_COLUMNS);
    private final JCheckBox closeCheckbox = new JCheckBox("Close");
    private final JComboBox<ButtonRow> rowCombo = new JComboBox<>();
    private final JComboBox<MacroButtonType> buttonType = new JComboBox<>();
    private final HotkeyButton hotkeyButton = new HotkeyButton();

    // Internal
    private final CardLayout displayLayout = new CardLayout();
    private final JPanel displayPanel = new JPanel(displayLayout);
    private final JButton shiftUpButton = new IconButton(DefaultIcon.ARROW_UP);
    private final JButton shiftDownButton = new IconButton(DefaultIcon.ARROW_DOWN);
    private final JButton deleteButton = new IconButton(DefaultIcon.CLOSE);

    public MacroCustomizerPanel(AddRemoveContainer<?> parent) {
        super(parent);
        setLayout(new GridBagLayout());

        // Labels
        JLabel lmbLabel = new JLabel("LMB");
        JLabel rmbLabel = new JLabel("RMB");
        ThemeManager.addStickyCombo(iconCombo);
        buttonText.setText("~");
        displayPanel.add(iconCombo, MacroButtonType.ICON.toString());
        displayPanel.add(buttonText, MacroButtonType.TEXT.toString());

        // Add items to combos
        for (ButtonRow row : ButtonRow.values()) rowCombo.addItem(row);
        for (CustomIcon icon : CustomIcon.values()) iconCombo.addItem(ThemeManager.getColorIcon(icon.path()));
        for (MacroButtonType type : MacroButtonType.values()) buttonType.addItem(type);

        // Delete & Shift Buttons
        GridBagConstraints gc = ZUtil.getGC();
        add(deleteButton, gc);
        gc.gridx++;
        gc.insets = new Insets(0, 0, 0, 2);
        add(shiftUpButton, gc);
        gc.gridy++;
        add(shiftDownButton, gc);
        gc.gridx++;
        gc.gridy = 0;
        gc.insets.right = 0;

        // Mouse Labels
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

        // Button type & icon/text
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;
        add(buttonType, gc);
        gc.gridy++;
        add(displayPanel, gc);
        gc.gridx++;
        gc.gridy = 0;

        // Row & Hotkey
        add(rowCombo, gc);
        gc.gridy++;
        add(new ButtonWrapper(hotkeyButton), gc);
        gc.gridx++;
        gc.gridy = 0;
        add(closeCheckbox, gc);
        gc.gridx++;

        updateUI();
        addListeners();
    }

    private void addListeners() {
        shiftUpButton.addActionListener(e -> shiftUp(shiftUpButton));
        shiftDownButton.addActionListener(e -> shiftDown(shiftDownButton));
        deleteButton.addActionListener(e -> {
            removeFromParent();
            ThemeManager.removeStickyCombo(iconCombo);
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
        button.buttonType = (MacroButtonType) buttonType.getSelectedItem();
        button.text = buttonText.getText();
        button.hotkeyData = hotkeyButton.getData();
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
        hotkeyButton.setData(macro.hotkeyData);
        closeCheckbox.setSelected(macro.close);
    }

    @Override
    public void updateUI() {
        super.updateUI();
        if (iconCombo != null) {
            int selectedIndex = iconCombo.getSelectedIndex();
            iconCombo.removeAllItems();
            for (CustomIcon icon : CustomIcon.values()) {
                iconCombo.addItem(ThemeManager.getColorIcon(icon.path()));
            }
            iconCombo.setSelectedIndex(selectedIndex);
        }
        setBorder(BorderFactory.createLineBorder(UIManager.getColor("Separator.foreground")));
    }


}
