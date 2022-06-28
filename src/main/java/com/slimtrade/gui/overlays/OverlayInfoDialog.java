package com.slimtrade.gui.overlays;

import com.slimtrade.core.References;
import com.slimtrade.core.enums.Anchor;
import com.slimtrade.core.enums.AppState;
import com.slimtrade.core.enums.ExpandDirection;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.ColorManager;
import com.slimtrade.core.utility.TradeUtil;
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
    private final JButton cancelButton = new JButton("Cancel");
    private final JButton restoreDefaultButton = new JButton("Restore Default");
    private final JButton saveButton = new JButton("Save");
    private final JComboBox<ExpandDirection> expandCombo = new LimitCombo<>();
    private final JComboBox<Anchor> menubarAnchorCombo = new LimitCombo<>();

    public OverlayInfoDialog() {
        super();
        // Panels
        JPanel infoPanel = new JPanel(new GridBagLayout());
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        JPanel outerPanel = new JPanel(new GridBagLayout());
        for (ExpandDirection direction : ExpandDirection.values()) expandCombo.addItem(direction);
        for (Anchor anchor : Anchor.values()) menubarAnchorCombo.addItem(anchor);


        // Info Panel
        GridBagConstraints gc = ZUtil.getGC();
        infoPanel.add(new JLabel("Click and drag the example message and menubar."), gc);
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

        comboPanel.add(new JLabel("Menubar Anchor Corner"), gc);
        gc.gridx++;
        gc.insets.left = comboSpacer;
        comboPanel.add(menubarAnchorCombo, gc);
        gc.insets.left = 0;
        gc.gridx = 0;
        gc.gridy++;

        // Outer Panel
        gc = ZUtil.getGC();
        outerPanel.add(infoPanel, gc);
        gc.gridy++;
        gc.insets.top = 10;
        outerPanel.add(comboPanel, gc);
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

        restoreDefaultButton.addActionListener(e -> {
            FrameManager.messageOverlay.setLocation(References.DEFAULT_MESSAGE_LOCATION);
            FrameManager.menubarOverlay.setLocation(References.DEFAULT_MENUBAR_LOCATION);
            menubarAnchorCombo.setSelectedItem(Anchor.TOP_LEFT);
            expandCombo.setSelectedItem(ExpandDirection.DOWNWARDS);
        });

        saveButton.addActionListener(e -> save());
        menubarAnchorCombo.addActionListener(e -> FrameManager.menubarOverlay.setAnchor((Anchor) menubarAnchorCombo.getSelectedItem()));
    }

    @Override
    public void onThemeChange() {
        contentPanel.setBorder(BorderFactory.createLineBorder(UIManager.getColor("Separator.foreground"), 1));
    }

    @Override
    public void save() {
        SaveManager.overlaySaveFile.data.messageLocation = FrameManager.messageOverlay.getLocation();
        SaveManager.overlaySaveFile.data.expandDirection = (ExpandDirection) expandCombo.getSelectedItem();
        SaveManager.overlaySaveFile.data.menubarAnchor = (Anchor) menubarAnchorCombo.getSelectedItem();
        SaveManager.overlaySaveFile.data.menubarLocation = FrameManager.menubarOverlay.getAnchorPoint(SaveManager.overlaySaveFile.data.menubarAnchor);

        // Update Other UI
        FrameManager.messageManager.setAnchorPoint(SaveManager.overlaySaveFile.data.messageLocation);
        FrameManager.messageManager.refresh();
        TradeUtil.applyAnchorPoint(FrameManager.menubarDialog, SaveManager.overlaySaveFile.data.menubarLocation, SaveManager.overlaySaveFile.data.menubarAnchor);
        TradeUtil.applyAnchorPoint(FrameManager.menubarIcon, SaveManager.overlaySaveFile.data.menubarLocation, SaveManager.overlaySaveFile.data.menubarAnchor);
        FrameManager.menubarDialog.rebuild();
        FrameManager.setWindowVisibility(AppState.RUNNING);
        SaveManager.overlaySaveFile.saveToDisk();
    }

    @Override
    public void load() {
        FrameManager.messageOverlay.setLocation(SaveManager.overlaySaveFile.data.messageLocation);
        TradeUtil.applyAnchorPoint(FrameManager.menubarOverlay, SaveManager.overlaySaveFile.data.menubarLocation, SaveManager.overlaySaveFile.data.menubarAnchor);
//        FrameManager.menubarOverlay.setLocation(SaveManager.overlaySaveFile.data.menubarLocation);
        expandCombo.setSelectedItem(SaveManager.overlaySaveFile.data.expandDirection);
        menubarAnchorCombo.setSelectedItem(SaveManager.overlaySaveFile.data.menubarAnchor);


        TradeUtil.applyAnchorPoint(FrameManager.menubarDialog, SaveManager.overlaySaveFile.data.menubarLocation, SaveManager.overlaySaveFile.data.menubarAnchor);
        TradeUtil.applyAnchorPoint(FrameManager.menubarIcon, SaveManager.overlaySaveFile.data.menubarLocation, SaveManager.overlaySaveFile.data.menubarAnchor);


    }

}
