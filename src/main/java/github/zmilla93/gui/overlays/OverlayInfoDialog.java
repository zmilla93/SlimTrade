package github.zmilla93.gui.overlays;

import github.zmilla93.core.References;
import github.zmilla93.core.enums.Anchor;
import github.zmilla93.core.enums.AppState;
import github.zmilla93.core.enums.ExpandDirection;
import github.zmilla93.core.enums.SliderRange;
import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.utility.TradeUtil;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.components.ErrorLabel;
import github.zmilla93.gui.components.LabeledSlider;
import github.zmilla93.gui.components.LimitCombo;
import github.zmilla93.gui.managers.FrameManager;
import github.zmilla93.gui.windows.BasicDialog;
import github.zmilla93.modules.saving.ISavable;
import github.zmilla93.modules.theme.ThemeManager;
import github.zmilla93.modules.theme.listeners.IFontChangeListener;
import github.zmilla93.modules.theme.listeners.IThemeListener;

import javax.swing.*;
import java.awt.*;

public class OverlayInfoDialog extends BasicDialog implements ISavable, IThemeListener, IFontChangeListener {

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
        expandCombo.addActionListener(e -> FrameManager.messageOverlay.setExpandDirection((ExpandDirection) expandCombo.getSelectedItem()));
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
        ExpandDirection expandDirection = (ExpandDirection) expandCombo.getSelectedItem();
        SaveManager.overlaySaveFile.data.messageLocation = FrameManager.messageOverlay.getAnchorPoint(expandDirection);
        SaveManager.overlaySaveFile.data.messageExpandDirection = expandDirection;
        SaveManager.overlaySaveFile.data.menubarAnchor = (Anchor) menubarAnchorCombo.getSelectedItem();
        SaveManager.overlaySaveFile.data.menubarLocation = FrameManager.menubarOverlay.getAnchorPoint(SaveManager.overlaySaveFile.data.menubarAnchor);
        SaveManager.overlaySaveFile.data.messageWidth = messageWidthSlider.getValue();

        // Update Other UI
        FrameManager.messageManager.refresh();
        TradeUtil.applyAnchorPoint(FrameManager.menuBarDialog, SaveManager.overlaySaveFile.data.menubarLocation, SaveManager.overlaySaveFile.data.menubarAnchor);
        TradeUtil.applyAnchorPoint(FrameManager.menuBarIcon, SaveManager.overlaySaveFile.data.menubarLocation, SaveManager.overlaySaveFile.data.menubarAnchor);
        FrameManager.menuBarDialog.rebuild();
        FrameManager.setWindowVisibility(AppState.RUNNING);
    }

    @Override
    public void load() {
        TradeUtil.applyAnchorPoint(FrameManager.messageOverlay, SaveManager.overlaySaveFile.data.messageLocation, SaveManager.overlaySaveFile.data.messageExpandDirection);
        TradeUtil.applyAnchorPoint(FrameManager.menubarOverlay, SaveManager.overlaySaveFile.data.menubarLocation, SaveManager.overlaySaveFile.data.menubarAnchor);
        expandCombo.setSelectedItem(SaveManager.overlaySaveFile.data.messageExpandDirection);
        menubarAnchorCombo.setSelectedItem(SaveManager.overlaySaveFile.data.menubarAnchor);
        messageWidthSlider.setValue(SaveManager.overlaySaveFile.data.messageWidth);

        TradeUtil.applyAnchorPoint(FrameManager.menuBarDialog, SaveManager.overlaySaveFile.data.menubarLocation, SaveManager.overlaySaveFile.data.menubarAnchor);
        TradeUtil.applyAnchorPoint(FrameManager.menuBarIcon, SaveManager.overlaySaveFile.data.menubarLocation, SaveManager.overlaySaveFile.data.menubarAnchor);
    }

    @Override
    public void onFontChanged() {
        pack();
        setLocationRelativeTo(null);
    }

}
