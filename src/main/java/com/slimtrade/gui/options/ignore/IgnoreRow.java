package com.slimtrade.gui.options.ignore;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.components.AddRemovePanel;
import com.slimtrade.gui.components.RemovablePanel;
import com.slimtrade.gui.custom.CustomLabel;
import com.slimtrade.gui.enums.DefaultIcons;
import com.slimtrade.gui.panels.BufferPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IgnoreRow extends RemovablePanel implements ActionListener, IColorable {

    private static final long serialVersionUID = 1L;
    private final int ITEM_MAX_WIDTH = 300;

    private final IgnoreData ignoreData;

    private final Timer timer;
    private JLabel timerLabel;

    public IgnoreRow(IgnoreData ignoreData, AddRemovePanel parent) {
        super(parent);
        if (ignoreData.indefinite) {
            timer = null;
        } else {
            timer = new Timer(60000, this);
        }
        this.ignoreData = ignoreData;
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        IconButton removeButton = new IconButton(DefaultIcons.CLOSE, 20);
        this.setRemoveButton(removeButton);
        JLabel itemLabel = new CustomLabel(ignoreData.getItemName());
        JPanel itemPanel = new JPanel(new GridBagLayout());
        itemPanel.setPreferredSize(new Dimension(ITEM_MAX_WIDTH, itemLabel.getPreferredSize().height));
        JLabel matchLabel = new CustomLabel(ignoreData.getMatchType().toString());
        if (ignoreData.getIndefinite()) {
            timerLabel = new CustomLabel("~");
        } else {
            timerLabel = new CustomLabel(ignoreData.getRemainingTime() + "m");
        }

        itemPanel.setBackground(ColorManager.CLEAR);
        itemPanel.add(itemLabel);

        this.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.NONE;
        gc.gridx = 0;
        gc.gridy = 0;

        gc.gridx++;
        gc.gridx++;
        this.add(new BufferPanel(160, 0), gc);
        gc.gridx++;
        this.add(new BufferPanel(60, 0), gc);
        gc.gridx = 0;
        gc.gridy++;
        this.add(removeButton, gc);
        gc.insets.left = 10;
        gc.insets.right = 10;
        gc.gridx++;
        this.add(itemPanel, gc);
        gc.gridx++;
        this.add(matchLabel, gc);
        gc.gridx++;
        this.add(timerLabel, gc);
        gc.gridx++;

        if (!ignoreData.indefinite) {
            timer.start();
        }

    }

    public IgnoreData getIgnoreData() {
        return this.ignoreData;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (ignoreData.indefinite) {
            timerLabel.setText("~");
            return;
        }
        int time = ignoreData.getRemainingTime();
        if (time <= 0) {
            this.dispose();
        } else {
            timerLabel.setText(time + "m");
        }
    }

    @Override
    public void updateColor() {
        super.updateColor();
        this.setBorder(ColorManager.BORDER_TEXT);
    }


}
