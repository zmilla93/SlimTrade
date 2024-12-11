package github.zmilla93.gui.options;

import github.zmilla93.core.data.IgnoreItemData;
import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.options.ignore.IgnoreInputPanel;
import github.zmilla93.gui.options.ignore.IgnoreRowComponents;
import github.zmilla93.modules.saving.ISavable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class IgnoreItemOptionPanel extends AbstractOptionPanel implements ISavable {

    private final IgnoreInputPanel ignoreInputPanel = new IgnoreInputPanel();
    private final JPanel ignoreContainer = new JPanel(new GridBagLayout());
    private final ArrayList<IgnoreRowComponents> componentRows = new ArrayList<>();
    private final GridBagConstraints gc = ZUtil.getGC();

    public IgnoreItemOptionPanel() {
        addHeader("Ignore New Item");
        addComponent(ignoreInputPanel);
        addVerticalStrutSmall();
        addComponent(new JLabel("Set minutes to 0 to ignore indefinitely."));
        addComponent(new JLabel("Right click the item name of an incoming trade to quick ignore."));
        addVerticalStrut();
        addHeader("Ignore List");
        addComponent(ignoreContainer);
        gc.anchor = GridBagConstraints.WEST;
        addListeners();
    }

    private void addListeners() {
        ignoreInputPanel.getIgnoreButton().addActionListener(e -> tryAddIgnoreItem(ignoreInputPanel.getIgnoreItem()));
    }

    public void tryAddIgnoreItem(IgnoreItemData item) {
        if (item.itemName.matches("")) return;
        if (!item.isInfinite() && item.getRemainingMinutes() <= 0) return;
        addNewRow(item);
        revalidate();
        repaint();
    }

    private void addNewRow(IgnoreItemData item) {
        IgnoreRowComponents row = new IgnoreRowComponents(this, item);
        componentRows.add(row);
        ignoreContainer.add(row.removeButton, gc);
        gc.gridx++;
        gc.insets.left = 10;
        ignoreContainer.add(row.matchTypeLabel, gc);
        gc.gridx++;
        gc.insets.left = 20;
        ignoreContainer.add(row.timeRemainingLabel, gc);
        gc.gridx++;
        ignoreContainer.add(row.itemNameLabel, gc);
        gc.gridx = 0;
        gc.gridy++;
        gc.insets.left = 0;
    }

    public void removeRow(IgnoreRowComponents row) {
        ignoreContainer.remove(row.removeButton);
        ignoreContainer.remove(row.timeRemainingLabel);
        ignoreContainer.remove(row.matchTypeLabel);
        ignoreContainer.remove(row.itemNameLabel);
        componentRows.remove(row);
        revalidate();
        repaint();
    }

    @Override
    public void save() {
        ArrayList<IgnoreItemData> ignoreItemData = new ArrayList<>();
        for (IgnoreRowComponents row : componentRows) {
            IgnoreItemData item = row.ignoreItemData;
            if (item.isInfinite() || item.getRemainingMinutes() > 0) ignoreItemData.add(item);
        }
        SaveManager.ignoreSaveFile.data.ignoreList = ignoreItemData;
    }

    @Override
    public void load() {
        ignoreContainer.removeAll();
        componentRows.clear();
        for (IgnoreItemData item : SaveManager.ignoreSaveFile.data.ignoreList) {
            tryAddIgnoreItem(item);
        }
        revalidate();
        repaint();
    }

}
