package com.slimtrade.gui.options.stash;

import com.slimtrade.core.data.StashTabData;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.gui.components.AddRemoveContainer;
import com.slimtrade.gui.components.PlainLabel;
import com.slimtrade.gui.options.AbstractOptionPanel;
import com.slimtrade.modules.saving.ISavable;

import javax.swing.*;
import java.util.ArrayList;

public class StashOptionPanel extends AbstractOptionPanel implements ISavable {

    private final JButton addButton = new JButton("Add Stash Tab");
    private final AddRemoveContainer<StashRow> tabContainer = new AddRemoveContainer<>();
    private final JCheckBox applyColorCheckbox = new JCheckBox("Also apply color to the trade notification panel.");

    public StashOptionPanel() {
        addHeader("Info");
        addComponent(new PlainLabel("Add stash tab names to apply a color to the item highlighter or mark quad tabs."));
        addComponent(new PlainLabel("Default white will use the color of the current theme."));
        addComponent(applyColorCheckbox);
        addVerticalStrut();
        addHeader("Stash Tab List");
        addComponent(addButton);
        addComponent(tabContainer);
        addListeners();
    }

    private void addListeners() {
        addButton.addActionListener(e -> {
            tabContainer.add(new StashRow(tabContainer));
            revalidate();
            repaint();
        });
    }

    @Override
    public void save() {
        ArrayList<StashTabData> stashTabs = new ArrayList<>();
        for (StashRow row : tabContainer.getComponentsTyped()) {
            stashTabs.add(row.getData());
        }
        SaveManager.settingsSaveFile.data.stashTabs = stashTabs;
        SaveManager.settingsSaveFile.data.applyStashColorToMessage = applyColorCheckbox.isSelected();
    }

    @Override
    public void load() {
        tabContainer.removeAll();
        for (StashTabData data : SaveManager.settingsSaveFile.data.stashTabs) {
            StashRow row = new StashRow(tabContainer);
            row.setData(data);
            tabContainer.add(row);
        }
        applyColorCheckbox.setSelected(SaveManager.settingsSaveFile.data.applyStashColorToMessage);
        revalidate();
        repaint();
    }

}
