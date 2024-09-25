package com.slimtrade.gui.ninja;

import com.slimtrade.core.enums.DefaultIcon;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.ninja.NinjaMouseAdapter;
import com.slimtrade.core.ninja.NinjaTabType;
import com.slimtrade.core.utility.GUIReferences;
import com.slimtrade.core.utility.NinjaInterface;
import com.slimtrade.gui.buttons.BasicIconButton;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.components.ButtonPanelPair;
import com.slimtrade.gui.components.StyledLabel;
import com.slimtrade.gui.components.ThemeLineBorder;
import com.slimtrade.gui.windows.BasicDialog;
import com.slimtrade.modules.saving.ISaveListener;
import com.slimtrade.modules.theme.IFontChangeListener;
import com.slimtrade.modules.theme.ThemeManager;
import org.jnativehook.GlobalScreen;
import org.jnativehook.mouse.NativeMouseEvent;

import javax.swing.*;
import java.awt.*;

/**
 * Parent window for displaying poe.ninja prices above the stash.
 * Contains many {@link NinjaGridPanel} instances.
 */
public class NinjaWindow extends BasicDialog implements ISaveListener, IFontChangeListener, NinjaMouseAdapter {

    private final JButton closeButton = new IconButton(DefaultIcon.CLOSE);
    private final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
    private final JButton syncButton = new IconButton(DefaultIcon.ARROW_SYNC);
    private final JLabel syncLabel = new StyledLabel("Synced 2m ago").italic();
    private NinjaMouseAdapter selectedPanel;

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardPanel = new JPanel(cardLayout);

    private final String NULL_PANEL_NAME = "NULL";

    public NinjaWindow() {
        // FIXME: Temp sync
        NinjaInterface.sync();
        setBackground(ThemeManager.TRANSPARENT);

        JPanel syncAndClosePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        syncAndClosePanel.add(syncLabel);
        syncAndClosePanel.add(Box.createHorizontalStrut(GUIReferences.HORIZONTAL_INSET_SMALL));
        syncAndClosePanel.add(syncButton);
        syncAndClosePanel.add(closeButton);
        syncButton.setEnabled(false);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(buttonPanel, BorderLayout.WEST);
        southPanel.add(syncAndClosePanel, BorderLayout.EAST);
        southPanel.setBorder(new ThemeLineBorder(0, 1, 1, 1));

        // Card Panel
        addCard(null, new IconButton(DefaultIcon.EYE));
        ButtonPanelPair selectedPanel = addCard(NinjaTabType.CURRENCY, "/icons/edits/currency.png");
        addCard(NinjaTabType.FRAGMENTS, "/currency/Essence_Scarab_of_Ascent.png");
        addCard(NinjaTabType.ESSENCE, "/currency/Deafening_Essence_of_Anguish.png");
        addCard(NinjaTabType.DELVE, "/currency/Aberrant_Fossil.png");
        addCard(NinjaTabType.BLIGHT, "/currency/Prismatic_Oil.png");
        addCard(NinjaTabType.DELIRIUM, "/currency/Delirium_Orb.png");
        addCard(NinjaTabType.ULTIMATUM, "/currency/Imbued_Catalyst.png");

        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(cardPanel, BorderLayout.CENTER);
        contentPanel.add(southPanel, BorderLayout.SOUTH);

        pack();
        updateBounds();
        setVisible(true);
        addListeners();
        SaveManager.stashSaveFile.addListener(this);
        ThemeManager.addFontListener(this);

        changePanel((NinjaGridPanel) selectedPanel.panel, selectedPanel.button);
        GlobalScreen.addNativeMouseListener(this);
        GlobalScreen.addNativeMouseMotionListener(this);
    }

    private void addListeners() {
        closeButton.addActionListener(e -> setVisible(false));
    }

    private void changePanel(NinjaGridPanel panel, JButton button) {
        for (Component component : buttonPanel.getComponents()) component.setEnabled(true);
        button.setEnabled(false);
        String panelName = panel.tabType == null ? NULL_PANEL_NAME : panel.tabType.toString();
        cardLayout.show(cardPanel, panelName);
        selectedPanel = panel;
        if (panel.tabType != null) NinjaInterface.sync(panel.tabType.dependencies);
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
        button.addActionListener(e -> changePanel(panel, button));
        return new ButtonPanelPair(button, panel);
    }

    private void updateBounds() {
        Rectangle rect = SaveManager.stashSaveFile.data.gridRect;
        setLocation(rect.getLocation());
        pack();
    }

    @Override
    public void onSave() {
        updateBounds();
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
