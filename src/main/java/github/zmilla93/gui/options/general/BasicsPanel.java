package github.zmilla93.gui.options.general;

import github.zmilla93.core.enums.AppState;
import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.components.ComponentPanel;
import github.zmilla93.gui.components.HotkeyButton;
import github.zmilla93.gui.managers.FrameManager;
import github.zmilla93.modules.saving.ISavable;

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
        JPanel buttonPanel = new ComponentPanel(editOverlayButton, editStashLocationButton);
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
