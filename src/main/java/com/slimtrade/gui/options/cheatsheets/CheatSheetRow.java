package com.slimtrade.gui.options.cheatsheets;

import com.slimtrade.core.data.CheatSheetData;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.basic.HotkeyButton;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.components.AddRemoveContainer;
import com.slimtrade.gui.components.AddRemovePanel;

import javax.swing.*;
import java.awt.*;

public class CheatSheetRow extends AddRemovePanel {

    private CheatSheetData data;
    private IconButton removeButton = new IconButton("/icons/default/closex64.png");
    private HotkeyButton hotkeyButton = new HotkeyButton();

    public CheatSheetRow(AddRemoveContainer parent, CheatSheetData data) {
        super(parent);
        this.data = data;
        JLabel fileLabel = new JLabel(data.fileName);
        hotkeyButton.setData(data.hotkeyData);
        setLayout(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        add(removeButton, gc);
        gc.insets.left = 10;
        gc.gridx++;
        add(fileLabel, gc);
        gc.gridx++;
        add(hotkeyButton, gc);
        gc.gridx++;
    }

    public CheatSheetData getData() {
        return data;
    }

}
