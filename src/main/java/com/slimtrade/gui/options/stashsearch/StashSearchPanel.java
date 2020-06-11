package com.slimtrade.gui.options.stashsearch;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.enums.StashTabColor;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.components.AddRemovePanel;
import com.slimtrade.gui.components.BasicRemovablePanel;
import com.slimtrade.gui.custom.CustomLabel;
import com.slimtrade.gui.options.ISaveable;
import com.slimtrade.gui.options.general.LabelComponentPanel;
import com.slimtrade.gui.options.hotkeys.HotkeyInputPane;
import com.slimtrade.gui.panels.ContainerPanel;

import javax.swing.*;
import java.awt.*;

public class StashSearchPanel extends ContainerPanel implements ISaveable {

    JLabel info1 = new CustomLabel("Quickly search phrases inside your stash to help with sorting.");

    private SearchInputPanel searchInputPanel = new SearchInputPanel();
    private AddRemovePanel addRemovePanel = new AddRemovePanel();
    private HotkeyInputPane hotkeyInputPane = new HotkeyInputPane();


    public StashSearchPanel() {

        JLabel toggleOverlayLabel = new CustomLabel("Show Sorting Window Hotkey");
        LabelComponentPanel togglePanel = new LabelComponentPanel(toggleOverlayLabel, hotkeyInputPane, 40);

        container.add(searchInputPanel, gc);
        gc.gridy++;
        gc.insets.top = 5;
        container.add(info1, gc);
        gc.gridy++;
        container.add(togglePanel, gc);
        gc.gridy++;
        container.add(addRemovePanel, gc);

        searchInputPanel.getAddButton().addActionListener(e -> {
            if (searchInputPanel.getSearchName().replaceAll("\\s+", "").length() > 0
                    && searchInputPanel.getSearchTerms().replaceAll("\\s+", "").length() > 0) {
                addRow(searchInputPanel.getSearchName(), searchInputPanel.getSearchTerms(), searchInputPanel.getColor());
                searchInputPanel.clearText();
            }
        });

        load();

    }

    private void addRow(String searchName, String searchTerms, StashTabColor color) {
        BasicRemovablePanel panel = new BasicRemovablePanel(true);
        JPanel colorPanel = new JPanel(new GridBagLayout());
        panel.addItem(new CustomLabel(searchName), 100);
        panel.addItem(new CustomLabel(searchTerms, false), 200);
        if (color == StashTabColor.ZERO) {
            colorPanel.add(new CustomLabel("~"));
            colorPanel.setOpaque(false);
            panel.addItem(colorPanel, 30);
        } else {
            colorPanel.setBackground(color.getBackground());
            panel.addCenteredItem(colorPanel, 30);
        }
        StashSearchData data = new StashSearchData();
        data.searchName = searchName;
        data.searchTerms = searchTerms;
        data.color = color;
        panel.setData(data);
        panel.bindShiftButtons(addRemovePanel);
        addRemovePanel.addRemovablePanel(panel);
        ColorManager.recursiveColor(panel);
    }

    @Override
    public void save() {
        App.saveManager.saveFile.stashSearchData.clear();
        for (Component c : addRemovePanel.getComponents()) {
            if (c instanceof BasicRemovablePanel) {
                if (c.isVisible()) {
                    StashSearchData data = (StashSearchData) ((BasicRemovablePanel) c).getData();
                    App.saveManager.saveFile.stashSearchData.add(data);
                }
            } else {
                remove(c);
            }
        }
        App.saveManager.saveFile.stashSearchHotkey = hotkeyInputPane.getHotkeyData();
        FrameManager.stashSearchWindow.refresh();
    }

    @Override
    public void load() {
        addRemovePanel.removeAll();
        for (StashSearchData data : App.saveManager.saveFile.stashSearchData) {
            addRow(data.searchName, data.searchTerms, data.color);
        }
        hotkeyInputPane.updateHotkey(App.saveManager.saveFile.stashSearchHotkey);
    }

}
