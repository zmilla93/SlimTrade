package com.slimtrade.gui.options.searching;

import com.slimtrade.core.CommonText;
import com.slimtrade.core.References;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.components.AddRemoveContainer;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.gui.options.AbstractOptionPanel;
import com.slimtrade.modules.saving.ISavable;

import javax.swing.*;
import java.util.ArrayList;

public class StashSearchOptionPanel extends AbstractOptionPanel implements ISavable {

    protected final StashSearchSettingsPanel settingsPanel = new StashSearchSettingsPanel();
    private final AddRemoveContainer<StashSearchGroupPanel> entryContainer = new AddRemoveContainer<>();
    private final JButton poeRegexButton = new JButton("Path of Regex");

    public StashSearchOptionPanel() {
        entryContainer.setUseDragBorder(false);
        addHeader("Info");
        addComponent(new JLabel("Pastes search terms into any POE window with a search bar (stashes, skill tree, vendors, etc)."));
        addComponent(new JLabel("Search groups can be separate windows, or a single combined window with a group selector."));
        addComponent(new JLabel(CommonText.DEFAULT_WHITE_TEXT));
        addComponent(poeRegexButton);
        addVerticalStrut();
        addHeader("Settings");
        addComponent(settingsPanel);
        addVerticalStrut();
        addHeader("Search Groups");
        addComponent(entryContainer);
        addListeners();
    }

    private void addListeners() {
        poeRegexButton.addActionListener(e -> ZUtil.openLink(References.POE_REGEX_LINK));
        settingsPanel.newSearchGroupButton.addActionListener(e -> {
            String error = tryAddNewGroup();
            settingsPanel.setError(error);
        });
        settingsPanel.modeCombo.addActionListener(e -> {
            StashSearchWindowMode mode = (StashSearchWindowMode) settingsPanel.modeCombo.getSelectedItem();
            boolean visibility = mode == StashSearchWindowMode.SEPARATE;
            for (StashSearchGroupPanel panel : entryContainer.getComponentsTyped()) {
                panel.updateHotkeyVisibility(visibility);
            }
        });
    }

    private String tryAddNewGroup() {
        String name = settingsPanel.getNewSearchGroupName();
        if (name.equals("")) return "Enter a valid group name!";
        if (isDuplicateName(name)) return "Duplicate name, group names must be unique!";
        entryContainer.add(new StashSearchGroupPanel(this, name));
        settingsPanel.clearText();
        return null;
    }

    public boolean isDuplicateName(String name) {
        for (StashSearchGroupPanel panel : entryContainer.getComponentsTyped()) {
            if (name.equals(panel.getGroupName())) return true;
        }
        return false;
    }

    public int getNextId() {
        int id = 1;
        boolean found = false;
        while (!found) {
            found = true;
            for (StashSearchGroupPanel panel : entryContainer.getComponentsTyped()) {
                if (panel.getData().id == id) {
                    id++;
                    found = false;
                    break;
                }
            }
        }
        return id;
    }

    @Override
    public void save() {
        ArrayList<StashSearchGroupData> data = new ArrayList<>();
        for (StashSearchGroupPanel groupPanel : entryContainer.getComponentsTyped()) {
            groupPanel.applyPendingGroupRename();
            data.add(groupPanel.getData());
        }
        // FIXME : Settings panel is saved twice. Doesn't really matter
        settingsPanel.save();
        SaveManager.settingsSaveFile.data.stashSearchData = data;
        FrameManager.buildSearchWindows();
    }

    @Override
    public void load() {
        entryContainer.removeAll();
        boolean groupHotkeyVisibility = SaveManager.settingsSaveFile.data.stashSearchWindowMode == StashSearchWindowMode.SEPARATE;
        for (StashSearchGroupData data : SaveManager.settingsSaveFile.data.stashSearchData) {
            entryContainer.add(new StashSearchGroupPanel(this, data, groupHotkeyVisibility));
        }
    }

}
