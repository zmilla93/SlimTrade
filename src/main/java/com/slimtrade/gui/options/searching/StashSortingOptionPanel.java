package com.slimtrade.gui.options.searching;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.gui.components.AddRemoveContainer;
import com.slimtrade.gui.components.StyledLabel;
import com.slimtrade.gui.options.AbstractOptionPanel;
import com.slimtrade.modules.saving.ISavable;

import java.util.ArrayList;

public class StashSortingOptionPanel extends AbstractOptionPanel implements ISavable {

    protected final StashSortingSettingsPanel settingsPanel = new StashSortingSettingsPanel();
    private final AddRemoveContainer<StashSortingGroupPanel> entryContainer = new AddRemoveContainer<>();

    public StashSortingOptionPanel() {
        addHeader("Info");
        addComponent(new StyledLabel("Pastes search terms into any POE window with a search bar (stashes, skill tree, vendors, etc)."));
        addComponent(new StyledLabel("Search groups can be separate windows, or a combined window with a group selector."));
        addComponent(new StyledLabel("Default white will match color theme."));
        addVerticalStrut();
        addHeader("Settings");
        addComponent(settingsPanel);
        addVerticalStrut();
        addHeader("Search Groups");
        addComponent(entryContainer);
        addListeners();
    }

    private void addListeners() {
        settingsPanel.newSearchGroupButton.addActionListener(e -> {
            String error = tryAddNewGroup();
            settingsPanel.setError(error);
        });
        settingsPanel.modeCombo.addActionListener(e -> {
            StashSortingWindowMode mode = (StashSortingWindowMode) settingsPanel.modeCombo.getSelectedItem();
            boolean visibility = mode == StashSortingWindowMode.SEPARATE;
            for (StashSortingGroupPanel panel : entryContainer.getComponentsTyped()) {
                panel.updateHotkeyVisibility(visibility);
            }
        });
    }

    private String tryAddNewGroup() {
        String name = settingsPanel.getNewSearchGroupName();
        if (name.equals("")) return "Enter a valid group name!";
        if (isDuplicateName(name)) return "Duplicate name, group names must be unique!";
        entryContainer.add(new StashSortingGroupPanel(entryContainer, this, name));
        settingsPanel.clearText();
        return null;
    }

    public boolean isDuplicateName(String name) {
        for (StashSortingGroupPanel panel : entryContainer.getComponentsTyped()) {
            if (name.equals(panel.getGroupName())) return true;
        }
        return false;
    }

    @Override
    public void save() {
        ArrayList<StashSearchGroupData> data = new ArrayList<>();
        for (StashSortingGroupPanel groupPanel : entryContainer.getComponentsTyped()) {
            groupPanel.applyPendingGroupRename();
            data.add(groupPanel.getData());
        }
        SaveManager.settingsSaveFile.data.stashSearchData = data;
        // FIXME : refreshButtons is currently broken
//        FrameManager.stashSortingWindow.refreshButtons();
    }

    @Override
    public void load() {
        entryContainer.removeAll();
        boolean groupHotkeyVisibility = SaveManager.settingsSaveFile.data.stashSearchWindowMode == StashSortingWindowMode.SEPARATE;
        for (StashSearchGroupData data : SaveManager.settingsSaveFile.data.stashSearchData) {
            entryContainer.add(new StashSortingGroupPanel(entryContainer, this, data, groupHotkeyVisibility));
        }
    }

}
