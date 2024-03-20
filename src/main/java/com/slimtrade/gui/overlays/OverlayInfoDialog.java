package com.slimtrade.gui.overlays;

import com.slimtrade.core.References;
import com.slimtrade.core.enums.Anchor;
import com.slimtrade.core.enums.AppState;
import com.slimtrade.core.enums.ExpandDirection;
import com.slimtrade.core.enums.SliderRange;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.TradeUtil;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.components.ErrorLabel;
import com.slimtrade.gui.components.LabeledSlider;
import com.slimtrade.gui.components.LimitCombo;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.gui.windows.AbstractDialog;
import com.slimtrade.modules.saving.ISavable;
import com.slimtrade.modules.theme.IFontChangeListener;
import com.slimtrade.modules.theme.IThemeListener;
import com.slimtrade.modules.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;

public class OverlayInfoDialog extends AbstractDialog implements ISavable, IThemeListener, IFontChangeListener {

    // Buttons
    private final JButton cancelButton = new JButton("Cancel");
    private final JButton restoreDefaultButton = new JButton("Restore Defaults");
    private final JButton saveButton = new JButton("Save");
    private final JComboBox<ExpandDirection> expandCombo = new LimitCombo<>();
    private final JComboBox<Anchor> menubarAnchorCombo = new LimitCombo<>();
    private final JSlider messageWidthSlider;

    public OverlayInfoDialog() {
        super();
        // Panels
        JPanel infoPanel = new JPanel(new GridBagLayout());
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        JPanel outerPanel = new JPanel(new GridBagLayout());
        for (ExpandDirection direction : ExpandDirection.values()) expandCombo.addItem(direction);
        for (Anchor anchor : Anchor.values()) menubarAnchorCombo.addItem(anchor);
        LabeledSlider sliderPanel = new LabeledSlider(SliderRange.MESSAGE_WIDTH, "px");
        messageWidthSlider = sliderPanel.slider;
        sliderPanel.setPreferredWidth(170);
        setFocusable(true);
        setFocusableWindowState(true);

        // Info Panel
        GridBagConstraints gc = ZUtil.getGC();
        infoPanel.add(new JLabel("Click and drag the example message and menubar."), gc);
        gc.gridy++;
        infoPanel.add(new ErrorLabel("Hold SHIFT to lock the window to the current monitor."), gc);

        // Button Panel
        gc = ZUtil.getGC();
        buttonPanel.add(restoreDefaultButton, gc);
        gc.gridx++;
        gc.insets.left = 20;
        buttonPanel.add(cancelButton, gc);
        gc.gridx++;
        buttonPanel.add(saveButton, gc);

        // Combo Panel
        int comboSpacer = 14;
        JPanel comboPanel = new JPanel(new GridBagLayout());
        gc = ZUtil.getGC();
        gc.fill = GridBagConstraints.HORIZONTAL;

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

        comboPanel.add(new JLabel("Message Width"), gc);
        gc.gridx++;
        gc.insets.left = comboSpacer;
        comboPanel.add(sliderPanel, gc);
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

        adjustBorder();
        pack();
        setLocationRelativeTo(null);
        ThemeManager.addThemeListener(this);
        ThemeManager.addFontListener(this);
        SaveManager.overlaySaveFile.registerSavableContainer(this);
        addListeners();
    }

    private void addListeners() {
        cancelButton.addActionListener(e -> {
            FrameManager.setWindowVisibility(AppState.RUNNING);
            load();
        });
        messageWidthSlider.addChangeListener(e -> {
            FrameManager.messageOverlay.notificationPanel.setWidth(messageWidthSlider.getValue());
            FrameManager.messageOverlay.pack();
        });
        restoreDefaultButton.addActionListener(e -> restoreDefaults());
        saveButton.addActionListener(e -> SaveManager.overlaySaveFile.saveToDisk());
        menubarAnchorCombo.addActionListener(e -> FrameManager.menubarOverlay.setAnchor((Anchor) menubarAnchorCombo.getSelectedItem()));
    }

    public void restoreDefaults() {
        FrameManager.messageOverlay.setLocation(References.DEFAULT_MESSAGE_LOCATION);
        FrameManager.menubarOverlay.setLocation(References.DEFAULT_MENUBAR_LOCATION);
        messageWidthSlider.setValue(SliderRange.MESSAGE_WIDTH.START);
        menubarAnchorCombo.setSelectedItem(Anchor.TOP_LEFT);
        expandCombo.setSelectedItem(ExpandDirection.DOWNWARDS);
    }

    private void adjustBorder() {
        contentPanel.setBorder(BorderFactory.createLineBorder(UIManager.getColor("Separator.foreground"), 2));
    }

    @Override
    public void onThemeChange() {
        adjustBorder();
    }

    // FIXME: Should make other overlay editing components implement ISavable to make code structure more consistent.
    //        This also means they would need to register themselves with OverlaySaveFile.
    // FIXME: Should have MessageManager, MenubarDialog, etc implement save listeners so they control themselves.
    @Override
    public void save() {
        SaveManager.overlaySaveFile.data.messageLocation = FrameManager.messageOverlay.getLocation();
        SaveManager.overlaySaveFile.data.messageExpandDirection = (ExpandDirection) expandCombo.getSelectedItem();
        SaveManager.overlaySaveFile.data.menubarAnchor = (Anchor) menubarAnchorCombo.getSelectedItem();
        SaveManager.overlaySaveFile.data.menubarLocation = FrameManager.menubarOverlay.getAnchorPoint(SaveManager.overlaySaveFile.data.menubarAnchor);
        SaveManager.overlaySaveFile.data.messageWidth = messageWidthSlider.getValue();

        // Update Other UI
        FrameManager.messageManager.setAnchorPoint(SaveManager.overlaySaveFile.data.messageLocation);
        FrameManager.messageManager.refresh();
        TradeUtil.applyAnchorPoint(FrameManager.menubarDialog, SaveManager.overlaySaveFile.data.menubarLocation, SaveManager.overlaySaveFile.data.menubarAnchor);
        TradeUtil.applyAnchorPoint(FrameManager.menubarIcon, SaveManager.overlaySaveFile.data.menubarLocation, SaveManager.overlaySaveFile.data.menubarAnchor);
        FrameManager.menubarDialog.rebuild();
        FrameManager.setWindowVisibility(AppState.RUNNING);
    }

    @Override
    public void load() {
        FrameManager.messageOverlay.setLocation(SaveManager.overlaySaveFile.data.messageLocation);
        TradeUtil.applyAnchorPoint(FrameManager.menubarOverlay, SaveManager.overlaySaveFile.data.menubarLocation, SaveManager.overlaySaveFile.data.menubarAnchor);
        expandCombo.setSelectedItem(SaveManager.overlaySaveFile.data.messageExpandDirection);
        menubarAnchorCombo.setSelectedItem(SaveManager.overlaySaveFile.data.menubarAnchor);
        messageWidthSlider.setValue(SaveManager.overlaySaveFile.data.messageWidth);

        TradeUtil.applyAnchorPoint(FrameManager.menubarDialog, SaveManager.overlaySaveFile.data.menubarLocation, SaveManager.overlaySaveFile.data.menubarAnchor);
        TradeUtil.applyAnchorPoint(FrameManager.menubarIcon, SaveManager.overlaySaveFile.data.menubarLocation, SaveManager.overlaySaveFile.data.menubarAnchor);
    }

    @Override
    public void onFontChanged() {
        pack();
        setLocationRelativeTo(null);
    }

}
