package com.slimtrade.gui.options.searching;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.gui.components.*;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.gui.options.AbstractOptionPanel;
import com.slimtrade.modules.saving.ISavable;

import java.util.ArrayList;

public class StashSortingOptionPanel extends AbstractOptionPanel implements ISavable {

    private StashSortInputPanel inputPanel = new StashSortInputPanel();
    private final StashSortingSettingsPanel settingsPanel = new StashSortingSettingsPanel();
    //    private final AddRemoveContainer dataContainer = new AddRemoveContainer();
    private final AddRemoveContainer entryContainer = new AddRemoveContainer();
//    private final HotkeyButton hotkeyButton = new HotkeyButton();

    public StashSortingOptionPanel() {
        addHeader("Info");
        addComponent(new StyledLabel("Pastes search terms into any POE window with a search bar (stashes, skill tree, vendors, etc)."));
        addComponent(new StyledLabel("Search groups can be separate windows, or a combined window with a group selector."));
        addComponent(new StyledLabel("Default white will match color theme."));
//        addComponent(new ButtonWrapper(hotkeyButton));
        addVerticalStrut();
        addHeader("Settings");
        addComponent(settingsPanel);
        addVerticalStrut();
        addHeader("Search Groups");
        addComponent(entryContainer);
        addListeners();
    }

    private void addListeners() {
//        inputPanel.getSubmitButton().addActionListener(e -> dataContainer.add(new OLD_StashSortRow(dataContainer, inputPanel.getData())));
        settingsPanel.newSearchGroupButton.addActionListener(e -> entryContainer.add(new StashSortingGroupPanel(entryContainer, "Cool Name")));
    }

    @Override
    public void save() {
        ArrayList<StashSortData> dataList = new ArrayList<>();
//        for (Component c : dataContainer.getComponents()) {
//            if (c instanceof OLD_StashSortRow) {
//                StashSortData sortData = ((OLD_StashSortRow) c).getData();
//                dataList.add(sortData);
//            }
//        }
        SaveManager.settingsSaveFile.data.stashSortData = dataList;
        FrameManager.stashSortingWindow.refreshButtons();
//        SaveManager.settingsSaveFile.data.stashSortHotkey = hotkeyButton.getData();
    }

    @Override
    public void load() {
//        dataContainer.removeAll();
//        for (StashSortData data : SaveManager.settingsSaveFile.data.stashSortData) {
//            dataContainer.add(new OLD_StashSortRow(dataContainer, data));
//        }
//        hotkeyButton.setData(SaveManager.settingsSaveFile.data.stashSortHotkey);
    }
}
