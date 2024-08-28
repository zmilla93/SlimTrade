package com.slimtrade.gui.ninja;

import com.slimtrade.core.enums.DefaultIcon;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.GUIReferences;
import com.slimtrade.core.utility.NinjaInterface;
import com.slimtrade.gui.buttons.BasicIconButton;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.components.StyledLabel;
import com.slimtrade.gui.ninja.panels.NinjaCurrencyPanel;
import com.slimtrade.gui.ninja.panels.NinjaDummyPanel;
import com.slimtrade.gui.ninja.panels.NinjaFragmentsPanel;
import com.slimtrade.gui.windows.BasicDialog;
import com.slimtrade.modules.saving.ISaveListener;
import com.slimtrade.modules.theme.IFontChangeListener;
import com.slimtrade.modules.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;

/**
 * Parent window for displaying poe.ninja prices above the stash.
 * Contains many {@link AbstractNinjaGridPanel} instances.
 */
public class NinjaWindow extends BasicDialog implements ISaveListener, IFontChangeListener {

    private final JButton closeButton = new IconButton(DefaultIcon.CLOSE);
    private final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
    private final JButton syncButton = new IconButton(DefaultIcon.ARROW_SYNC);
    private final JButton hideButton = new IconButton(DefaultIcon.EYE);
    private final JButton currencyButton = new BasicIconButton(() -> "/icons/edits/currency.png");
    private final JButton fragmentsButton = new BasicIconButton(() -> "/currency/Essence_Scarab_Of_Ascent.png");
    private final JButton essenceButton = new BasicIconButton(() -> "/currency/Deafening_Essence_of_Anguish.png");
    private final JButton delveButton = new BasicIconButton(() -> "/currency/Aberrant_Fossil.png");
    private final JButton blightButton = new BasicIconButton(() -> "/currency/Prismatic_Oil.png");
    private final JButton deliriumButton = new BasicIconButton(() -> "/currency/Delirium_Orb.png");
    private final JButton ultimatumButton = new BasicIconButton(() -> "/currency/Imbued_Catalyst.png");
    private final JLabel syncLabel = new StyledLabel("Synced 2m ago").italic();

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardPanel = new JPanel(cardLayout);

    private final String NULL_PANEL_NAME = "NULL";
    private final NinjaFragmentsPanel fragmentsPanel = new NinjaFragmentsPanel();
    private final NinjaCurrencyPanel currencyPanel = new NinjaCurrencyPanel();
    private final NinjaDummyPanel dummyPanel = new NinjaDummyPanel();

    public NinjaWindow() {
        // FIXME: Temp sync
        NinjaInterface.sync();
        setBackground(ThemeManager.TRANSPARENT);

        buttonPanel.add(hideButton);
        buttonPanel.add(currencyButton);
        buttonPanel.add(fragmentsButton);
        buttonPanel.add(essenceButton);
        buttonPanel.add(delveButton);
        buttonPanel.add(blightButton);
        buttonPanel.add(deliriumButton);
        buttonPanel.add(ultimatumButton);

        JPanel syncAndClosePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        syncAndClosePanel.add(syncLabel);
        syncAndClosePanel.add(Box.createHorizontalStrut(GUIReferences.HORIZONTAL_INSET_SMALL));
        syncAndClosePanel.add(syncButton);
        syncAndClosePanel.add(closeButton);
        syncButton.setEnabled(false);

        JPanel southPanel = new JPanel(new BorderLayout());
//        southPanel.add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.NORTH);
        southPanel.add(buttonPanel, BorderLayout.WEST);
        southPanel.add(syncAndClosePanel, BorderLayout.EAST);

        // Card Panel

        addCard(dummyPanel, hideButton);
        addCard(currencyPanel, currencyButton);
        addCard(fragmentsPanel, fragmentsButton);

        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(cardPanel, BorderLayout.CENTER);
        contentPanel.add(southPanel, BorderLayout.SOUTH);

        pack();
        updateBounds();
        setVisible(true);
        addListeners();
        SaveManager.stashSaveFile.addListener(this);
        ThemeManager.addFontListener(this);

        changePanel(fragmentsPanel, fragmentsButton);
    }

    private void addListeners() {
        closeButton.addActionListener(e -> setVisible(false));
    }

    private void changePanel(AbstractNinjaGridPanel panel, JButton button) {
        for (Component component : buttonPanel.getComponents()) component.setEnabled(true);
        button.setEnabled(false);
        String panelName = panel.getTabType() == null ? NULL_PANEL_NAME : panel.getTabType().toString();
        cardLayout.show(cardPanel, panelName);
    }

    private void addCard(AbstractNinjaGridPanel panel, JButton button) {
        String panelName = panel.getTabType() == null ? NULL_PANEL_NAME : panel.getTabType().toString();
        System.out.println(panelName);
        cardPanel.add(panel, panelName);
        button.addActionListener(e -> changePanel(panel, button));
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

}
