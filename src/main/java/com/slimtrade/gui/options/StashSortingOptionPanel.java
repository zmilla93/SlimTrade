package com.slimtrade.gui.options;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.gui.basic.HotkeyButton;
import com.slimtrade.gui.components.AddRemoveContainer;
import com.slimtrade.gui.components.ButtonWrapper;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.gui.stashsorting.StashSortData;
import com.slimtrade.gui.stashsorting.StashSortInputPanel;
import com.slimtrade.gui.stashsorting.StashSortRow;
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
        addPanel(new JLabel("A window with buttons that paste search terms into your stash."));
        addPanel(new JLabel("Add a hotkey to open the window itself."));
        addPanel(new JLabel("Default white will match color theme."));
        addPanel(new ButtonWrapper(hotkeyButton));
        addVerticalStrut();
        addHeader("New Search");
        addPanel(inputPanel);
        addVerticalStrut();
        addHeader("Search Terms");
        addPanel(dataContainer);
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
