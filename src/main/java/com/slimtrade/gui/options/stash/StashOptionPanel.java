package com.slimtrade.gui.options.stash;

import com.slimtrade.core.data.StashTabData;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.gui.components.AddRemoveContainer;
import com.slimtrade.gui.options.AbstractOptionPanel;
import com.slimtrade.modules.saving.ISavable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StashOptionPanel extends AbstractOptionPanel implements ISavable {

    private JButton addButton = new JButton("Add Stash Tab");
    private AddRemoveContainer tabContainer = new AddRemoveContainer();
    private JCheckBox applyColorCheckbox = new JCheckBox("Also apply color to the trade notification panel.");

    public StashOptionPanel() {
        addHeader("Info");
        addPanel(new JLabel("Add stash tab names to apply a color to the item highlighter or mark quad tabs."));
        addPanel(new JLabel("Default white will use the color of the current theme."));
        addPanel(applyColorCheckbox);
        addVerticalStrut();
        addHeader("Stash Tab List");
        addPanel(addButton);
        addPanel(tabContainer);
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
        for (Component c : tabContainer.getComponents()) {
            if (c instanceof StashRow) {
                StashTabData data = ((StashRow) c).getData();
                stashTabs.add(data);
            }
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
