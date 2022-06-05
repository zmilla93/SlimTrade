package com.slimtrade.gui.options;

import com.slimtrade.core.data.IgnoreItem;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.gui.components.AddRemoveContainer;
import com.slimtrade.gui.options.ignore.IgnoreInputPanel;
import com.slimtrade.gui.options.ignore.IgnoreRow;
import com.slimtrade.modules.saving.ISavable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class IgnoreItemOptionPanel extends AbstractOptionPanel implements ISavable {

    private IgnoreInputPanel ignoreInputPanel = new IgnoreInputPanel();
    private AddRemoveContainer ignoreContainer = new AddRemoveContainer();

    public IgnoreItemOptionPanel() {
        addHeader("Ignore New Item");
        addPanel(ignoreInputPanel);
        addSmallVerticalStrut();
        addPanel(new JLabel("Set minutes to 0 to ignore indefinitely."));
        addPanel(new JLabel("Right click item names of incoming trades to quick ignore."));
        addVerticalStrut();
        addHeader("Ignore List");
        addPanel(ignoreContainer);
        addListeners();
    }

    public void tryAddIgnore(IgnoreItem item) {
        if (item.itemName.matches("\\s+")) return;
        if (!item.isInfinite() && item.getRemainingMinutes() <= 0) return;
        ignoreContainer.add(new IgnoreRow(ignoreContainer, item));
        revalidate();
        repaint();
    }

    private void addListeners() {
        ignoreInputPanel.getIgnoreButton().addActionListener(e -> tryAddIgnore(ignoreInputPanel.getIgnoreItem()));
    }

    @Override
    public void save() {
        ArrayList<IgnoreItem> ignoreItems = new ArrayList<>();
        for (Component c : ignoreContainer.getComponents()) {
            if (c instanceof IgnoreRow) {
                IgnoreItem item = ((IgnoreRow) c).ignoreItem;
                if (item.isInfinite() || item.getRemainingMinutes() > 0) ignoreItems.add(item);
            }
        }
        SaveManager.ignoreSaveFile.data.ignoreList = ignoreItems;
    }

    @Override
    public void load() {
        ignoreContainer.removeAll();
        for (IgnoreItem item : SaveManager.ignoreSaveFile.data.ignoreList) {
            tryAddIgnore(item);
        }
    }

}
