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
        labelPanel.add(new CustomLabel(data.getCleanName()));
        labelPanel.setOpaque(false);

        // Build UI
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.WEST;
        gc.weightx = 1;
        gc.fill = GridBagConstraints.BOTH;
        gc.insets.left = 40;
        gc.insets.right = 40;
        add(labelPanel, gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST;
        gc.fill = GridBagConstraints.NONE;
        gc.weightx = 0;
        int i = 2;
        gc.insets = new Insets(i, 0, i, i);
        add(hotkeyInputPane, gc);

    }

    @Override
    public void updateColor() {
        setBackground(ColorManager.BACKGROUND);
        setBorder(ColorManager.BORDER_TEXT);
    }
}
