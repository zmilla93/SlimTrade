package com.slimtrade.gui.options.ignore;

import com.slimtrade.core.data.IgnoreItem;
import com.slimtrade.core.enums.MatchType;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.components.AddRemoveContainer;
import com.slimtrade.gui.components.AddRemovePanel;

import javax.swing.*;
import java.awt.*;

public class IgnoreRow extends AddRemovePanel {

    public final IgnoreItem ignoreItem;
    private int remainingTime;

    private JPanel infoPanel = new JPanel(new GridBagLayout());
    private final JButton removeButton = new IconButton("/icons/default/closex64.png");
    private final JLabel timerLabel = new JLabel();
    private final JLabel itemNameLabel = new JLabel();
    private final JLabel matchLabel = new JLabel();
    private Timer timer;

    private Component nameStrut;
    private Component matchStrut;
    private Component timerStrut;

    private final GridBagConstraints gc = ZUtil.getGC();

    public IgnoreRow(AddRemoveContainer parent, IgnoreItem ignoreItem) {
        super(parent);
        itemNameLabel.setText(ignoreItem.itemName);
        this.ignoreItem = ignoreItem;
        GridBagConstraints gc = ZUtil.getGC();
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 0;
        gc.insets.left = 10;
        infoPanel.add(matchLabel, gc);
        gc.gridx++;
        infoPanel.add(timerLabel, gc);
        gc.gridx++;
        gc.weightx = 1;
        infoPanel.add(itemNameLabel, gc);
        gc.weightx = 0;
        gc.gridx++;
        gc.insets.left = 0;
        setLayout(new BorderLayout());
        add(removeButton, BorderLayout.WEST);
        add(infoPanel, BorderLayout.CENTER);
        if (!ignoreItem.isInfinite()) {
            remainingTime = ignoreItem.getRemainingMinutes();
            timerLabel.setText(Integer.toString(remainingTime));
            timer = new Timer(1000 * 60, e -> {
                remainingTime--;
                timerLabel.setText(remainingTime + "m");
                if (remainingTime <= 0) {
                    timer.stop();
                    removeFromParent();
                    SaveManager.ignoreSaveFile.data.buildCache();
                }
            });
            timer.start();
        }
        addListeners();
        updateUI();
    }

    private void addListeners() {
        removeButton.addActionListener(e -> {
            if (timer != null) timer.stop();
            removeFromParent();
        });
    }

    @Override
    public void updateUI() {
        super.updateUI();
        if (ignoreItem == null) return;
        matchLabel.setText(MatchType.CONTAINS_TEXT.toString());
        timerLabel.setText("100m");
        matchLabel.setPreferredSize(matchLabel.getPreferredSize());
        timerLabel.setPreferredSize(timerLabel.getPreferredSize());

//        itemNameLabel.setText(ignoreItem.itemName);
        matchLabel.setText(ignoreItem.matchType.toString());
        String timerText = ignoreItem.isInfinite() ? "~" : ignoreItem.getRemainingMinutes() + "m";
        timerLabel.setText(timerText);
//        gc.gridx = 0;
//        gc.gridy = 1;
    }

}
