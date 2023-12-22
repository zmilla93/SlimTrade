package com.slimtrade.gui.options;

import com.slimtrade.core.data.IgnoreItem;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.gui.components.AddRemoveContainer;
import com.slimtrade.gui.options.ignore.IgnoreInputPanel;
import com.slimtrade.gui.options.ignore.IgnoreRow;
import com.slimtrade.modules.saving.ISavable;

import javax.swing.*;
import java.util.ArrayList;

public class IgnoreItemOptionPanel extends AbstractOptionPanel implements ISavable {

    private final IgnoreInputPanel ignoreInputPanel = new IgnoreInputPanel();
    private final AddRemoveContainer<IgnoreRow> ignoreContainer = new AddRemoveContainer<>();

    public IgnoreItemOptionPanel() {
        addHeader("Ignore New Item");
        addComponent(ignoreInputPanel);
        addVerticalStrutSmall();
        addComponent(new JLabel("Set minutes to 0 to ignore indefinitely."));
        addComponent(new JLabel("Right click item names of incoming trades to quick ignore."));
        addVerticalStrut();
        addHeader("Ignore List");
        addComponent(ignoreContainer);
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
        for (IgnoreRow row : ignoreContainer.getComponentsTyped()) {
            IgnoreItem item = row.ignoreItem;
            if (item.isInfinite() || item.getRemainingMinutes() > 0) ignoreItems.add(item);
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
