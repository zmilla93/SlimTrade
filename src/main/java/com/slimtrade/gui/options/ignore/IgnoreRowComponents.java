package com.slimtrade.gui.options.ignore;

import com.slimtrade.core.data.IgnoreItemData;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.options.IgnoreItemOptionPanel;

import javax.swing.*;

public class IgnoreRowComponents {

    // UI Elements
    public final JButton removeButton = new IconButton("/icons/default/closex64.png");
    public final JLabel matchTypeLabel;
    public final JLabel timeRemainingLabel;
    public final JLabel itemNameLabel;
    public final IgnoreItemData ignoreItemData;

    // Internal
    private Timer timer;
    private int remainingTime;

    public IgnoreRowComponents(IgnoreItemOptionPanel parent, IgnoreItemData ignoreItemData) {
        this.ignoreItemData = ignoreItemData;
        timeRemainingLabel = new JLabel();
        matchTypeLabel = new JLabel(ignoreItemData.matchType.toString());
        itemNameLabel = new JLabel(ignoreItemData.itemName);
        updateTimeRemainingLabel();
        // Remove Button
        removeButton.addActionListener(e -> parent.removeRow(this));
        // Timer
        remainingTime = ignoreItemData.getRemainingMinutes();
        if(!ignoreItemData.isInfinite()){
            timer = new Timer(1000 * 60, e -> {
                remainingTime--;
                updateTimeRemainingLabel();
                if (remainingTime <= 0) {
                    timer.stop();
                    parent.removeRow(this);
                    SaveManager.ignoreSaveFile.data.buildCache();
                }
            });
            timer.start();
        }
    }

    private void updateTimeRemainingLabel() {
        if (ignoreItemData.isInfinite()) timeRemainingLabel.setText("~");
        else timeRemainingLabel.setText(ignoreItemData.getRemainingMinutes() + "m");
    }

}
