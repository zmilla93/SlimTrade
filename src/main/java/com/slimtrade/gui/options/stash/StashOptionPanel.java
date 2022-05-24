package com.slimtrade.gui.options.stash;

import com.slimtrade.core.data.StashTabData;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.options.AbstractOptionPanel;
import com.slimtrade.modules.saving.ISavable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StashOptionPanel extends AbstractOptionPanel implements ISavable {

    private JButton addButton = new JButton("Add Stash Tab");
    private JPanel tabContainer = new JPanel(new GridBagLayout());
    private GridBagConstraints gc = ZUtil.getGC();

    public StashOptionPanel() {
        addHeader("Info");
        addPanel(new JLabel("Add stash tabs to apply a color to the item highlighter or mark quad tabs."));
        addPanel(new JLabel("Default white will use the color of the current theme."));
        addVerticalStrut();
        addHeader("Stash Tab List");
        addPanel(addButton);
        addPanel(tabContainer);
        addListeners();
    }

    private void addListeners() {
        Container self = this;
        addButton.addActionListener(e -> {
            gc.gridy = tabContainer.getComponentCount();
            tabContainer.add(new StashRow(tabContainer), gc);
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
    }

    @Override
    public void load() {
        tabContainer.removeAll();
        for (StashTabData data : SaveManager.settingsSaveFile.data.stashTabs) {
            StashRow row = new StashRow(tabContainer);
            row.setData(data);
            gc.gridy = tabContainer.getComponentCount();
            tabContainer.add(row, gc);
        }
        revalidate();
        repaint();
    }

}
