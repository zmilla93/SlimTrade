package com.slimtrade.gui.overlays;

import com.slimtrade.core.References;
import com.slimtrade.core.enums.AppState;
import com.slimtrade.core.enums.ExpandDirection;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.ColorManager;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.components.LimitCombo;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.gui.windows.AbstractDialog;
import com.slimtrade.modules.colortheme.IThemeListener;
import com.slimtrade.modules.saving.ISavable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OverlayInfoDialog extends AbstractDialog implements IThemeListener, ISavable {

    // Buttons
    JButton cancelButton = new JButton("Cancel");
    JButton restoreDefaultButton = new JButton("Restore Default");
    JButton saveButton = new JButton("Save");
    private JComboBox<ExpandDirection> expandCombo = new LimitCombo<>();

    public OverlayInfoDialog() {
        super();
        // Panels
        JPanel infoPanel = new JPanel(new GridBagLayout());
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        JPanel outerPanel = new JPanel(new GridBagLayout());
        for (ExpandDirection direction : ExpandDirection.values()) expandCombo.addItem(direction);

        // Info Panel
        GridBagConstraints gc = ZUtil.getGC();
        infoPanel.add(new JLabel("Click and drag the example message."), gc);
        gc.gridy++;
        infoPanel.add(new JLabel("Hold SHIFT to lock the message to the current monitor."), gc);

        // Button Panel
        gc = ZUtil.getGC();
        buttonPanel.add(cancelButton, gc);
        gc.gridx++;
        gc.insets.left = 20;
        buttonPanel.add(restoreDefaultButton, gc);
        gc.gridx++;
        buttonPanel.add(saveButton, gc);

        // Combo Panel
        int comboSpacer = 10;
        JPanel comboPanel = new JPanel(new GridBagLayout());
        gc = ZUtil.getGC();
        comboPanel.add(new JLabel("Message Expand Direction"), gc);
        gc.gridx++;
        gc.insets.left = comboSpacer;
        comboPanel.add(expandCombo, gc);
        gc.insets.left = 0;
        gc.gridx = 0;
        gc.gridy++;

        // Outer Panel
        gc = ZUtil.getGC();
        outerPanel.add(comboPanel, gc);
        gc.gridy++;
        gc.insets.top = 10;
        outerPanel.add(infoPanel, gc);
        gc.gridy++;
        outerPanel.add(buttonPanel, gc);

        contentPanel.setLayout(new GridBagLayout());
        gc = ZUtil.getGC();
        int inset = 20;
        gc.insets = new Insets(inset, inset, inset, inset);
        contentPanel.add(outerPanel, gc);
        pack();
        setLocationRelativeTo(null);
        ColorManager.addThemeListener(this);
        addListeners();
    }

    private void addListeners() {
        cancelButton.addActionListener(e -> {
            FrameManager.setWindowVisibility(AppState.RUNNING);
            FrameManager.messageOverlay.setLocation(FrameManager.messageManager.getLocation());
        });

        restoreDefaultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrameManager.messageOverlay.setLocation(References.DEFAULT_MESSAGE_LOCATION);
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
    }

    @Override
    public void onThemeChange() {
        contentPanel.setBorder(BorderFactory.createLineBorder(UIManager.getColor("Separator.foreground"), 1));
    }

    @Override
    public void save() {
        Point targetPos = FrameManager.messageOverlay.getLocation();
        SaveManager.overlaySaveFile.data.messageLocation = targetPos;
        SaveManager.overlaySaveFile.data.expandDirection = (ExpandDirection) expandCombo.getSelectedItem();
        FrameManager.messageManager.setAnchorPoint(targetPos);
        FrameManager.messageManager.refresh();
        FrameManager.setWindowVisibility(AppState.RUNNING);
        SaveManager.overlaySaveFile.saveToDisk();
    }

    @Override
    public void load() {
        FrameManager.messageOverlay.setLocation(SaveManager.overlaySaveFile.data.messageLocation);
        expandCombo.setSelectedItem(SaveManager.overlaySaveFile.data.expandDirection);
    }

}
