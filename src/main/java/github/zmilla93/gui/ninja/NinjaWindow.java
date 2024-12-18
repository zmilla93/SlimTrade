package github.zmilla93.gui.ninja;

import github.zmilla93.core.enums.DefaultIcon;
import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.ninja.NinjaMouseAdapter;
import github.zmilla93.core.ninja.NinjaTabType;
import github.zmilla93.core.utility.NinjaInterface;
import github.zmilla93.gui.buttons.BasicIconButton;
import github.zmilla93.gui.buttons.IconButton;
import github.zmilla93.gui.components.ButtonPanelPair;
import github.zmilla93.gui.components.StyledLabel;
import github.zmilla93.gui.components.ThemeLineBorder;
import github.zmilla93.gui.windows.BasicDialog;
import github.zmilla93.modules.saving.ISaveListener;
import github.zmilla93.modules.theme.ThemeManager;
import github.zmilla93.modules.theme.listeners.IFontChangeListener;
import org.jnativehook.GlobalScreen;
import org.jnativehook.mouse.NativeMouseEvent;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Parent window for displaying poe.ninja prices above the stash.
 * Contains many {@link NinjaGridPanel} instances.
 */
public class NinjaWindow extends BasicDialog implements ISaveListener, IFontChangeListener, NinjaMouseAdapter {

    private final JButton closeButton = new IconButton(DefaultIcon.CLOSE);
    private final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
    private final JButton syncButton = new IconButton(DefaultIcon.ARROW_SYNC);
    private final JLabel syncLabel = new StyledLabel("Synced 2m ago").italic();
    private NinjaGridPanel selectedPanel;
    private ArrayList<ButtonPanelPair> buttonPanelPairs = new ArrayList<>();
    private HashMap<NinjaTabType, ButtonPanelPair> buttonPanelPairMap = new HashMap<>();

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardPanel = new JPanel(cardLayout);

    private final String NULL_PANEL_NAME = "NULL";

    public NinjaWindow() {
        setBackground(ThemeManager.TRANSPARENT);

        JPanel syncAndClosePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        syncAndClosePanel.add(closeButton);
        syncButton.setEnabled(false);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(buttonPanel, BorderLayout.WEST);
        southPanel.add(syncAndClosePanel, BorderLayout.EAST);
        southPanel.setBorder(new ThemeLineBorder(0, 1, 1, 1));

        // Card Panel
        addCard(null, new IconButton(DefaultIcon.EYE));
        ButtonPanelPair selectedPanel = addCard(NinjaTabType.CURRENCY, "/icons/edits/currency.png");
        addCard(NinjaTabType.FRAGMENT, "/currency/Essence_Scarab_of_Ascent.png");
        addCard(NinjaTabType.ESSENCE, "/currency/Deafening_Essence_of_Anguish.png");
        addCard(NinjaTabType.DELVE, "/currency/Aberrant_Fossil.png");
        addCard(NinjaTabType.BLIGHT, "/currency/Prismatic_Oil.png");
        addCard(NinjaTabType.DELIRIUM, "/currency/Delirium_Orb.png");
        addCard(NinjaTabType.ULTIMATUM, "/currency/Imbued_Catalyst.png");

        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(cardPanel, BorderLayout.CENTER);
        contentPanel.add(southPanel, BorderLayout.SOUTH);

        updateButtonVisibility();
        pack();
        updateBounds();
        setVisible(true);
        addListeners();
        SaveManager.stashSaveFile.addListener(this);
        SaveManager.settingsSaveFile.addListener(this);
        ThemeManager.addFontListener(this);

        changePanel(selectedPanel);
        GlobalScreen.addNativeMouseListener(this);
        GlobalScreen.addNativeMouseMotionListener(this);
    }

    private void addListeners() {
        closeButton.addActionListener(e -> setVisible(false));
    }

    private void changePanel(NinjaTabType tabType) {
        ButtonPanelPair pair = buttonPanelPairMap.get(tabType);
        changePanel(pair);
    }

    private void changePanel(ButtonPanelPair pair) {
        for (Component component : buttonPanel.getComponents()) component.setEnabled(true);
        pair.button.setEnabled(false);
        String panelName = pair.tabType == null ? NULL_PANEL_NAME : pair.tabType.toString();
        cardLayout.show(cardPanel, panelName);
        selectedPanel = pair.panel;
        if (pair.tabType != null) NinjaInterface.sync(pair.tabType.dependencies);
    }

    private ButtonPanelPair addCard(NinjaTabType tabType, String iconPath) {
        JButton button = new BasicIconButton(() -> iconPath);
        return addCard(tabType, button);
    }

    private ButtonPanelPair addCard(NinjaTabType tabType, JButton button) {
        NinjaGridPanel panel = new NinjaGridPanel(tabType);
        String panelName = tabType == null ? NULL_PANEL_NAME : tabType.toString();
        cardPanel.add(panel, panelName);
        buttonPanel.add(button);
        ButtonPanelPair pair = new ButtonPanelPair(button, panel, tabType);
        button.addActionListener(e -> changePanel(pair));
        buttonPanelPairs.add(pair);
        buttonPanelPairMap.put(tabType, pair);
        return pair;
    }

    private void updateBounds() {
        Rectangle rect = SaveManager.stashSaveFile.data.gridRect;
        setLocation(rect.getLocation());
        pack();
    }

    private void updateButtonVisibility() {
        // FIXME : Button visibility
//        buttonPanelPairMap.get(NinjaTabType.CURRENCY).button.setVisible(SaveManager.settingsSaveFile.data.ninjaEnableCurrencyTab);
//        buttonPanelPairMap.get(NinjaTabType.FRAGMENT).button.setVisible(SaveManager.settingsSaveFile.data.ninjaEnableFragmentTab);
//        buttonPanelPairMap.get(NinjaTabType.ESSENCE).button.setVisible(SaveManager.settingsSaveFile.data.ninjaEnableEssenceTab);
//        buttonPanelPairMap.get(NinjaTabType.DELVE).button.setVisible(SaveManager.settingsSaveFile.data.ninjaEnableDelveTab);
//        buttonPanelPairMap.get(NinjaTabType.BLIGHT).button.setVisible(SaveManager.settingsSaveFile.data.ninjaEnableBlightTab);
//        buttonPanelPairMap.get(NinjaTabType.DELIRIUM).button.setVisible(SaveManager.settingsSaveFile.data.ninjaEnableDeliriumTab);
//        buttonPanelPairMap.get(NinjaTabType.ULTIMATUM).button.setVisible(SaveManager.settingsSaveFile.data.ninjaEnableUltimatumTab);
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        if (selectedPanel != null && selectedPanel.tabType != null)
            NinjaInterface.sync(selectedPanel.tabType.dependencies);
    }

    @Override
    public void onSave() {
        updateBounds();
        updateButtonVisibility();
    }

    @Override
    public void onFontChanged() {
        pack();
    }


    @Override
    public void nativeMouseClicked(NativeMouseEvent nativeMouseEvent) {
        selectedPanel.nativeMouseClicked(nativeMouseEvent);
    }

    @Override
    public void nativeMousePressed(NativeMouseEvent nativeMouseEvent) {
        selectedPanel.nativeMousePressed(nativeMouseEvent);
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent nativeMouseEvent) {
        selectedPanel.nativeMouseReleased(nativeMouseEvent);
    }

    @Override
    public void nativeMouseMoved(NativeMouseEvent nativeMouseEvent) {
        selectedPanel.nativeMouseMoved(nativeMouseEvent);
    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent nativeMouseEvent) {
        selectedPanel.nativeMouseDragged(nativeMouseEvent);
    }

}
