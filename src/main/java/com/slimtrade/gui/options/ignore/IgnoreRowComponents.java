package com.slimtrade.gui.options.ignore;

import com.slimtrade.core.data.IgnoreItem;
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
    public final IgnoreItem ignoreItem;

    // Internal
    private Timer timer;
    private int remainingTime;

    public IgnoreRowComponents(IgnoreItemOptionPanel parent, IgnoreItem ignoreItem) {
        this.ignoreItem = ignoreItem;
        timeRemainingLabel = new JLabel();
        matchTypeLabel = new JLabel(ignoreItem.matchType.toString());
        itemNameLabel = new JLabel(ignoreItem.itemName);
        updateTimeRemainingLabel();
        // Timer
        remainingTime = ignoreItem.getRemainingMinutes();
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
        // Remove Button
        removeButton.addActionListener(e -> parent.removeRow(this));
    }

    private void updateTimeRemainingLabel() {
        if (ignoreItem.isInfinite()) timeRemainingLabel.setText("~");
        else timeRemainingLabel.setText(ignoreItem.getRemainingMinutes() + "m");
    }

}
