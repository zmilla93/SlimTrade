package github.zmilla93.gui.options.ignore;

import github.zmilla93.core.data.IgnoreItemData;
import github.zmilla93.core.enums.DefaultIcon;
import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.gui.buttons.IconButton;
import github.zmilla93.gui.options.IgnoreItemOptionPanel;

import javax.swing.*;

public class IgnoreRowComponents {

    // UI Elements
    public final JButton removeButton = new IconButton(DefaultIcon.CLOSE);
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
        if (!ignoreItemData.isInfinite()) {
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
