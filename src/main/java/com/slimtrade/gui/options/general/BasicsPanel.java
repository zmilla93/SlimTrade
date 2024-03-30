package com.slimtrade.gui.options.general;

import com.slimtrade.core.enums.AppState;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.components.ComponentPair;
import com.slimtrade.gui.components.HotkeyButton;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.modules.saving.ISavable;

import javax.swing.*;
import java.awt.*;

public class BasicsPanel extends JPanel implements ISavable {

    private final JCheckBox showGuildName = new JCheckBox();
    private final JCheckBox folderOffsetCheckbox = new JCheckBox("Using Stash Folders");
    private final HotkeyButton quickPasteHotkey = new HotkeyButton();
    private final JButton editOverlayButton = new JButton("Edit Message & Menubar Overlays");
    private final JButton editStashLocationButton = new JButton("Edit Stash Location");
    private final GridBagConstraints gc = ZUtil.getGC();

    public BasicsPanel() {
        setLayout(new GridBagLayout());
        gc.weightx = 1;
        gc.anchor = GridBagConstraints.WEST;

        // Add components
        JPanel buttonPanel = new ComponentPair(editOverlayButton, editStashLocationButton);
        addComponent(folderOffsetCheckbox);
        addComponent(buttonPanel);

        addListeners();
    }

    private void addListeners() {
        editOverlayButton.addActionListener(e -> FrameManager.setWindowVisibility(AppState.EDIT_OVERLAY));
        editStashLocationButton.addActionListener(e -> FrameManager.setWindowVisibility(AppState.EDIT_STASH));
    }

    private void addComponent(JComponent component) {
        add(component, gc);
        gc.gridy++;
    }

    @Override
    public void save() {
        SaveManager.settingsSaveFile.data.showGuildName = showGuildName.isSelected();
        SaveManager.settingsSaveFile.data.folderOffset = folderOffsetCheckbox.isSelected();
        SaveManager.settingsSaveFile.data.quickPasteHotkey = quickPasteHotkey.getData();
    }

    @Override
    public void load() {
        showGuildName.setSelected(SaveManager.settingsSaveFile.data.showGuildName);
        folderOffsetCheckbox.setSelected(SaveManager.settingsSaveFile.data.folderOffset);
        quickPasteHotkey.setData(SaveManager.settingsSaveFile.data.quickPasteHotkey);
    }

}
