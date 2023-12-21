package com.slimtrade.gui.options.searching;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.gui.basic.HotkeyButton;
import com.slimtrade.gui.components.AddRemoveContainer;
import com.slimtrade.gui.components.ButtonWrapper;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.gui.options.AbstractOptionPanel;
import com.slimtrade.modules.saving.ISavable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StashSortingOptionPanel extends AbstractOptionPanel implements ISavable {

    private StashSortInputPanel inputPanel = new StashSortInputPanel();
    private AddRemoveContainer dataContainer = new AddRemoveContainer();
    private HotkeyButton hotkeyButton = new HotkeyButton();

    public StashSortingOptionPanel() {
        addHeader("Info");
        addComponent(new JLabel("A window with buttons that paste search terms into your stash."));
        addComponent(new JLabel("Add a hotkey to open the window itself."));
        addComponent(new JLabel("Default white will match color theme."));
        addComponent(new ButtonWrapper(hotkeyButton));
        addVerticalStrut();
        addHeader("New Search");
        addComponent(inputPanel);
        addVerticalStrut();
        addHeader("Search Terms");
        addComponent(dataContainer);
        addListeners();
    }

    private void addListeners() {
        inputPanel.getSubmitButton().addActionListener(e -> dataContainer.add(new StashSortRow(dataContainer, inputPanel.getData())));
    }

    @Override
    public void save() {
        ArrayList<StashSortData> dataList = new ArrayList<>();
        for (Component c : dataContainer.getComponents()) {
            if (c instanceof StashSortRow) {
                StashSortData sortData = ((StashSortRow) c).getData();
                dataList.add(sortData);
            }
        }
        SaveManager.settingsSaveFile.data.stashSortData = dataList;
        FrameManager.stashSortingWindow.refreshButtons();
        SaveManager.settingsSaveFile.data.stashSortHotkey = hotkeyButton.getData();
    }

    @Override
    public void load() {
        dataContainer.removeAll();
        for (StashSortData data : SaveManager.settingsSaveFile.data.stashSortData) {
            dataContainer.add(new StashSortRow(dataContainer, data));
        }
        hotkeyButton.setData(SaveManager.settingsSaveFile.data.stashSortHotkey);
    }
}
