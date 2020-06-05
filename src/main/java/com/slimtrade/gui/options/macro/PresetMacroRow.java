package com.slimtrade.gui.options.macro;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.IColorable;
import com.slimtrade.gui.basic.GridBagPanel;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.custom.CustomLabel;
import com.slimtrade.gui.enums.ICacheImage;
import com.slimtrade.gui.options.hotkeys.HotkeyInputPane;

import javax.swing.*;
import java.awt.*;

public class PresetMacroRow extends GridBagPanel implements IColorable {

    private String title;
    private ICacheImage image;
    private String textLMB;
    private String textRMB;
    private boolean addHotkey;
    private JPanel innerPanel = new JPanel(new GridBagLayout());
    public HotkeyInputPane hotkeyInputPane;

    int col = 20;
    int colIncrease = 6;

    public PresetMacroRow(String text) {
        this.title = text;
    }

    public PresetMacroRow(ICacheImage image) {
        this.image = image;
    }

    public void setLMB(String text) {
        this.textLMB = text;
    }

    public void setRMB(String text) {
        this.textRMB = text;
    }

//    public void addHotkey(HotkeyInputPane hotkeyInputPane) {
//        this.hotkeyInputPane = hotkeyInputPane;
//    }

    public void buildPanel() {
        if (hotkeyInputPane != null) {
            col += colIncrease;
        }
        gc.gridwidth = 4;
//        innerPanel.add(Box.createHorizontalStrut(350), gc);
        gc.gridwidth = 1;
        gc.gridy++;
        if (image != null) {
            IconButton iconButton = new IconButton(image, 20);
            innerPanel.add(iconButton, gc);
        } else {
            innerPanel.add(new CustomLabel(title), gc);
        }
        gc.gridx++;
        gc.insets.left = 10;
        innerPanel.add(new CustomLabel(MacroCustomizerRow.LEFT_CLICK_TEXT), gc);
        gc.gridx++;
        gc.fill = GridBagConstraints.BOTH;
        JLabel textFieldLMB = new CustomLabel(false);
        textFieldLMB.setText(textLMB);
        innerPanel.add(textFieldLMB, gc);
        if (textRMB != null) {
            gc.gridx--;
            gc.gridy++;
            innerPanel.add(new CustomLabel(MacroCustomizerRow.RIGHT_CLICK_TEXT), gc);
            gc.gridx++;
            JLabel textFieldRMB = new CustomLabel(false);
            textFieldRMB.setText(textRMB);
            innerPanel.add(textFieldRMB, gc);
        }
        gc.fill = GridBagConstraints.NONE;
        gc.gridx++;
        gc.gridy = 1;
        if (hotkeyInputPane != null) {
            gc.gridheight = 2;
            innerPanel.add(hotkeyInputPane, gc);
            gc.gridheight = 1;
        }

        gc.gridx = 0;
        gc.gridy = 0;
        int i = 1;
        gc.insets = new Insets(i, i, i, i);
        this.add(innerPanel, gc);
    }

    @Override
    public void updateColor() {
        this.setBackground(ColorManager.BACKGROUND);
        innerPanel.setBackground(ColorManager.BACKGROUND);
        this.setBorder(BorderFactory.createLineBorder(ColorManager.LOW_CONTRAST_2));
    }
}
