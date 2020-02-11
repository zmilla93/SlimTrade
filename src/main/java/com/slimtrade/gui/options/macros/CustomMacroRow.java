package com.slimtrade.gui.options.macros;

import java.awt.*;

import javax.swing.*;

import com.slimtrade.App;
import com.slimtrade.core.SaveSystem.MacroButton;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.gui.basic.CustomCombo;
import com.slimtrade.gui.basic.CustomTextField;
import com.slimtrade.gui.components.AddRemovePanel;
import com.slimtrade.gui.components.RemovablePanel;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.enums.ButtonRow;
import com.slimtrade.gui.enums.PreloadedImage;
import com.slimtrade.gui.enums.PreloadedImageCustom;
import com.slimtrade.gui.panels.BufferPanel;

public class CustomMacroRow extends RemovablePanel implements IColorable {

    private static final long serialVersionUID = 1L;
    private JLabel nameLabel = new JLabel("Custom");
    private JLabel m1Label = new JLabel("Left Mouse");
    private JLabel m2Label = new JLabel("Right Mouse");
    private JTextField m1Text = new CustomTextField(30);
    private JTextField m2Text = new CustomTextField(30);
    private JComboBox<ImageIcon> iconCombo;
    private JComboBox<ButtonRow> rowCombo;
    private IconButton removeButton = new IconButton(PreloadedImage.CLOSE.getImage(), 20);

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
        for (PreloadedImageCustom i : PreloadedImageCustom.values()) {
            ImageIcon icon = new ImageIcon(i.getImage());
            iconCombo.addItem(icon);
        }
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
        App.eventManager.addColorListener(this);
        updateColor();
    }

    public ButtonRow getButtonRow() {
        return (ButtonRow) rowCombo.getSelectedItem();
    }

    public PreloadedImageCustom getButtonImage() {
        return PreloadedImageCustom.values()[iconCombo.getSelectedIndex()];
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

    public void setButtonImage(PreloadedImageCustom img) {
        if(img == null) {
            for(PreloadedImageCustom c : PreloadedImageCustom.values()) {
                img = c;
                break;
            }
        }
        int i = 0;
        for(PreloadedImageCustom c : PreloadedImageCustom.values()) {
            if(c == img) {
                break;
            }
            i++;
        }
        iconCombo.setSelectedIndex(i);
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

    @Override
    public void updateColor() {
        this.setBackground(ColorManager.BACKGROUND);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

}
