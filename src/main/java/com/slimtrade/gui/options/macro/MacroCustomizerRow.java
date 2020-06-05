package com.slimtrade.gui.options.macro;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.IColorable;
import com.slimtrade.core.saving.MacroButton;
import com.slimtrade.enums.ColorTheme;
import com.slimtrade.gui.basic.GridBagPanel;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.custom.CustomCheckbox;
import com.slimtrade.gui.custom.CustomCombo;
import com.slimtrade.gui.custom.CustomLabel;
import com.slimtrade.gui.custom.CustomTextField;
import com.slimtrade.gui.enums.ButtonRow;
import com.slimtrade.gui.enums.CustomIcons;
import com.slimtrade.gui.enums.DefaultIcons;
import com.slimtrade.gui.options.hotkeys.HotkeyInputPane;

import javax.swing.*;
import java.awt.*;

public class MacroCustomizerRow extends GridBagPanel implements IColorable {

    private final int rowHeight = 20;
    public static final String LEFT_CLICK_TEXT = "Left Click";
    public static final String RIGHT_CLICK_TEXT = "Right Click";

    public JButton upArrowButton = new IconButton(DefaultIcons.ARROW_UP, rowHeight);
    public JButton downArrowButton = new IconButton(DefaultIcons.ARROW_DOWN, rowHeight);
    public JButton closeButton = new IconButton(DefaultIcons.CLOSE, rowHeight);
    public JComboBox<ButtonRow> rowCombo = new CustomCombo<>();
    public CustomCombo<ImageIcon> iconCombo = new CustomCombo<>();
    public JCheckBox closeCheckbox = new CustomCheckbox();
    public HotkeyInputPane hotkeyInput = new HotkeyInputPane() {

    };

    public JTextField textLMB = new CustomTextField(25);
    public JTextField textRMB = new CustomTextField(25);

    private JPanel innerPanel = new JPanel(new GridBagLayout());
    private ColorTheme cachedColor;

    public MacroCustomizerRow() {
        gc.weightx = 1;
        upArrowButton = new IconButton(DefaultIcons.ARROW_UP, rowHeight);
        downArrowButton = new IconButton(DefaultIcons.ARROW_DOWN, rowHeight);
        for (ButtonRow b : ButtonRow.values()) {
            rowCombo.addItem(b);
        }
        for (CustomIcons i : CustomIcons.values()) {
            ImageIcon icon = new ImageIcon(i.getColorImage(ColorManager.TEXT));
            iconCombo.addItem(icon);
        }
        gc.anchor = GridBagConstraints.WEST;
        innerPanel.add(upArrowButton, gc);
        gc.gridy++;
        innerPanel.add(downArrowButton, gc);
        gc.anchor = GridBagConstraints.CENTER;
        gc.insets.left = 5;
        gc.gridx++;
        gc.gridy = 0;
        gc.gridheight = 2;
        innerPanel.add(rowCombo, gc);
        gc.gridx++;
        innerPanel.add(iconCombo, gc);
        gc.gridheight = 1;
        gc.gridx++;
        innerPanel.add(new CustomLabel(LEFT_CLICK_TEXT), gc);
        gc.gridy++;
        innerPanel.add(new CustomLabel(RIGHT_CLICK_TEXT), gc);
        gc.gridx++;
        gc.gridy = 0;
        innerPanel.add(textLMB, gc);
        gc.gridy++;
        gc.insets.top = 2;
        innerPanel.add(textRMB, gc);
        gc.insets.top = 0;
        gc.gridx++;
        gc.gridy = 0;
        gc.gridheight = 2;
        innerPanel.add(hotkeyInput, gc);
        gc.gridx++;
        innerPanel.add(closeCheckbox, gc);
        gc.gridheight = 1;
        gc.gridx++;
        gc.insets.right = 0;
        gc.anchor = GridBagConstraints.EAST;
        innerPanel.add(closeButton, gc);
        gc.anchor = GridBagConstraints.CENTER;
        gc.gridx = 0;
        int i = 1;
        gc.insets = new Insets(i, i, i, i);
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1;
        this.add(innerPanel, gc);
        gc.weightx = 0;
        gc.fill = GridBagConstraints.NONE;

        closeButton.addActionListener(e -> setVisible(false));

    }

    public void setEnabledAll(boolean state) {
        rowCombo.setEnabled(state);
        iconCombo.setEnabled(state);
        textLMB.setEnabled(state);
        textRMB.setEnabled(state);
        hotkeyInput.setEnabled(state);
        closeCheckbox.setEnabled(state);
        closeButton.setEnabled(state);
    }

    public MacroCustomizerRow(MacroButton data) {
        this();
        this.setMacroData(data);
    }

    public void setMacroData(MacroButton data) {
        rowCombo.setSelectedItem(data.row);
        // TODO : Icon
        textLMB.setText(data.leftMouseResponse);
        textRMB.setText(data.rightMouseResponse);
        hotkeyInput.updateHotkey(data.hotkeyData);
        closeCheckbox.setSelected(data.closeOnClick);
        for (int i = 0; i < CustomIcons.values().length; i++) {
            if (CustomIcons.values()[i] == data.image) {
                iconCombo.setSelectedIndex(i);
                break;
            }
        }
    }

    public MacroButton getMacroData() {
        ButtonRow row = (ButtonRow) rowCombo.getSelectedItem();
        int index = iconCombo.getSelectedIndex();
        CustomIcons img;
        if (index < 0) {
            img = CustomIcons.values()[0];
        } else {
            img = CustomIcons.values()[index];
        }

        return new MacroButton(row, textLMB.getText().trim(), textRMB.getText().trim(), img, hotkeyInput.getHotkeyData(), closeCheckbox.isSelected());
    }

    @Override
    public void updateColor() {
        if (cachedColor == ColorManager.getCurrentColorTheme()) {
            return;
        }
        cachedColor = ColorManager.getCurrentColorTheme();
        this.setBackground(ColorManager.BACKGROUND);
        innerPanel.setBackground(ColorManager.BACKGROUND);
        this.setBorder(BorderFactory.createLineBorder(ColorManager.LOW_CONTRAST_2));
        int sel = iconCombo.getSelectedIndex();
        iconCombo.removeAllItems();
        iconCombo.updateColor();
        for (CustomIcons i : CustomIcons.values()) {
            ImageIcon icon = new ImageIcon(i.getColorImage(ColorManager.TEXT));
            iconCombo.addItem(icon);
        }
        if (sel < iconCombo.getItemCount()) {
            iconCombo.setSelectedIndex(sel);
        }
    }
}
