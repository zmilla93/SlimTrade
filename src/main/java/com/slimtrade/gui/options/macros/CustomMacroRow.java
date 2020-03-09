package com.slimtrade.gui.options.macros;

import java.awt.*;

import javax.swing.*;

import com.slimtrade.App;
import com.slimtrade.core.saving.MacroButton;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.gui.basic.CustomCombo;
import com.slimtrade.gui.basic.CustomLabel;
import com.slimtrade.gui.basic.CustomTextField;
import com.slimtrade.gui.components.AddRemovePanel;
import com.slimtrade.gui.components.RemovablePanel;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.enums.ButtonRow;
import com.slimtrade.gui.enums.DefaultIcons;
import com.slimtrade.gui.enums.CustomIcons;
import com.slimtrade.gui.panels.BufferPanel;

public class CustomMacroRow extends RemovablePanel implements IColorable {

    private static final long serialVersionUID = 1L;
    private JLabel nameLabel = new CustomLabel("Custom");
    private JLabel m1Label = new CustomLabel("Left Click");
    private JLabel m2Label = new CustomLabel("Right Click");
    public JTextField m1Text = new CustomTextField(26);
    public JTextField m2Text = new CustomTextField(26);
    public JComboBox<ImageIcon> iconCombo;
    public JComboBox<ButtonRow> rowCombo;
    public IconButton removeButton = new IconButton(DefaultIcons.CLOSE, 20);

//	private boolean unsaved = true;
//	private boolean delete;

    public CustomMacroRow(AddRemovePanel parent) {
        super(parent);
        this.setLayout(new GridBagLayout());
        setRemoveButton(removeButton);
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridheight = 1;
        iconCombo = new CustomCombo<>();
//        for (CustomIcons i : CustomIcons.values()) {
//            ImageIcon icon = new ImageIcon(i.getColorImage(ColorManager.TEXT));
//            iconCombo.addItem(icon);
//        }

        rowCombo = new CustomCombo<>();
        for (ButtonRow row : ButtonRow.values()) {
            rowCombo.addItem(row);
        }
        this.add(new BufferPanel(10, 0), gc);
        gc.gridx++;
        this.add(rowCombo, gc);
        gc.gridx++;
        this.add(iconCombo, gc);
        gc.gridx++;
        this.add(new BufferPanel(10, 0), gc);
        gc.gridx++;
        this.add(m1Label, gc);
        gc.gridx++;
        this.add(new BufferPanel(10, 0), gc);
        gc.gridx++;
        this.add(m1Text, gc);
        gc.gridx++;
        this.add(new BufferPanel(10, 0), gc);
        this.add(removeButton, gc);

        //Second Row
        gc.insets.bottom = 2;
        gc.gridx = 1;
        gc.gridy = 1;
        gc.gridx += 3;
        this.add(m2Label, gc);
        gc.gridx += 2;
        this.add(m2Text, gc);
    }

    public ButtonRow getButtonRow() {
        int index = rowCombo.getSelectedIndex();
//        System.out.println("\tGetting Row from CustomMacroRow : (" + index + ") ");
        return (ButtonRow) rowCombo.getSelectedItem();
    }

    public CustomIcons getButtonImage() {
        int index = iconCombo.getSelectedIndex();
        CustomIcons img = CustomIcons.values()[index];
//        System.out.println("\tGetting Image from CustomMacroRow : (" + index + ") " + img);
        return img;
    }

    public String getTextLMB() {
        return m1Text.getText();
    }

    public String getTextRMB() {
        return m2Text.getText();
    }

    public void setButtonRow(ButtonRow row) {
        rowCombo.setSelectedItem(row);
    }

    public void setButtonImage(CustomIcons img) {
        System.out.println("Setting combo... " + img);
        iconCombo.setSelectedItem(img);
    }

    public void setTextLMB(String text) {
        m1Text.setText(text);
    }

    public void setTextRMB(String text) {
        m2Text.setText(text);
    }

    public MacroButton getMacroData() {
        return new MacroButton(getButtonRow(), getTextLMB(), getTextRMB(), getButtonImage());
    }

    public static boolean checkMatchingRows(CustomMacroRow row1, CustomMacroRow row2) {
        if(!(row1.getButtonRow() == row2.getButtonRow())) {
            return false;
        }
        if(!(row1.getButtonImage() == row2.getButtonImage())) {
            return false;
        }
        if(!(row1.getTextLMB().equals(row2.getTextLMB()))) {
            return false;
        }
        if(!(row1.getTextRMB().equals(row2.getTextRMB()))) {
            return false;
        }
        return true;
    }

    public void setEnabledAll(boolean state) {
        rowCombo.setEnabled(state);
        iconCombo.setEnabled(state);
        m1Text.setEnabled(state);
        m2Text.setEnabled(state);
        removeButton.setEnabled(state);
    }


    @Override
    public void updateColor() {
        super.updateColor();
        this.setBackground(ColorManager.BACKGROUND);
        this.setBorder(BorderFactory.createLineBorder(ColorManager.LOW_CONTRAST_2));
        iconCombo.removeAll();
        iconCombo.removeAllItems();
        for (CustomIcons i : CustomIcons.values()) {
            ImageIcon icon = new ImageIcon(i.getColorImage(ColorManager.TEXT));
            iconCombo.addItem(icon);
        }
    }

}
