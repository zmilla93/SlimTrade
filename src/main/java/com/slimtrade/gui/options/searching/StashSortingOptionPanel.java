package com.slimtrade.gui.options.searching;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.gui.components.AddRemoveContainer;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.gui.options.AbstractOptionPanel;
import com.slimtrade.gui.pinning.PinManager;
import com.slimtrade.modules.saving.ISavable;

import javax.swing.*;
import java.util.ArrayList;

public class StashSortingOptionPanel extends AbstractOptionPanel implements ISavable {

    protected final StashSortingSettingsPanel settingsPanel = new StashSortingSettingsPanel();
    private final AddRemoveContainer<StashSortingGroupPanel> entryContainer = new AddRemoveContainer<>();
    private final ArrayList<Integer> idsToReset = new ArrayList<Integer>();

    public StashSortingOptionPanel() {
        addHeader("Info");
        addComponent(new JLabel("Pastes search terms into any POE window with a search bar (stashes, skill tree, vendors, etc)."));
        addComponent(new JLabel("Search groups can be separate windows, or a combined window with a group selector."));
        addComponent(new JLabel("Default white will match color theme."));
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

    public int getNextId() {
        int id = 1;
        boolean found = false;
        while (!found) {
            found = true;
            for (StashSortingGroupPanel panel : entryContainer.getComponentsTyped()) {
                if (panel.getData().id == id) {
                    id++;
                    found = false;
                    break;
                }
            }
        }
        return id;
    }

    public void addIdToReset(int id) {
        idsToReset.add(id);
    }

    @Override
    public void save() {
        // TODO : Reset id data
        idsToReset.clear();
        ArrayList<StashSortingGroupPanel> panels = new ArrayList<>();
        ArrayList<StashSearchGroupData> data = new ArrayList<>();
        for (StashSortingGroupPanel groupPanel : entryContainer.getComponentsTyped()) {
            groupPanel.applyPendingGroupRename();
            panels.add(groupPanel);
            data.add(groupPanel.getData());
        }
        // FIXME : Should probably move to saving mode here so that rebuilding panels is done using the most recent data.
        //         Alternatively, could change the order that savables are triggered, but that might break something else.
        SaveManager.settingsSaveFile.data.stashSearchData = data;
        // FIXME : Pins need to be reworked. Currently they will get overwritten when switching to a different mode
        PinManager.storeSearchWindowPins();
        FrameManager.buildSearchWindows();
        PinManager.restoreSearchWindowPins(panels);
        // FIXME : Should perhaps move this to the main save action
        PinManager.applyPins();
    }

    @Override
    public void load() {
        entryContainer.removeAll();
        idsToReset.clear();
        boolean groupHotkeyVisibility = SaveManager.settingsSaveFile.data.stashSearchWindowMode == StashSortingWindowMode.SEPARATE;
        for (StashSearchGroupData data : SaveManager.settingsSaveFile.data.stashSearchData) {
            entryContainer.add(new StashSortingGroupPanel(entryContainer, this, data, groupHotkeyVisibility));
        }
    }

}
