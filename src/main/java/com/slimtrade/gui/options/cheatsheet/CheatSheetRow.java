package com.slimtrade.gui.options.cheatsheet;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.IColorable;
import com.slimtrade.gui.custom.CustomLabel;
import com.slimtrade.gui.options.hotkeys.HotkeyInputPane;

import javax.swing.*;
import java.awt.*;

public class CheatSheetRow extends JPanel implements IColorable {

    public CheatSheetData data;
    public HotkeyInputPane hotkeyInputPane = new HotkeyInputPane();

    public CheatSheetRow(CheatSheetData data) {
        super(new GridBagLayout());
        this.data = data;

        // Components
        JPanel labelPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1;
        gc.fill = GridBagConstraints.BOTH;
//        gc.insets.left = 100;
        labelPanel.add(new CustomLabel(data.getCleanName()), gc);
        labelPanel.setOpaque(false);

        // Build UI
//        gc.fill = GridBagConstraints.BOTH;
        gc.anchor = GridBagConstraints.WEST;
        gc.weightx = 1;

        gc.insets.left = 5;
//        gc.insets.right = 20;
        gc.fill = GridBagConstraints.BOTH;
        add(labelPanel, gc);

        gc.gridx++;
        int i = 2;
        gc.insets = new Insets(i, 0, i, i);
        gc.weightx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.fill = GridBagConstraints.NONE;
        add(hotkeyInputPane, gc);

    }

    @Override
    public void updateColor() {
        setBackground(ColorManager.BACKGROUND);
        setBorder(ColorManager.BORDER_LOW_CONTRAST_2);
    }
}
