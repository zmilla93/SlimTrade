package com.slimtrade.gui.ninja;

import com.slimtrade.core.enums.DefaultIcon;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.GUIReferences;
import com.slimtrade.core.utility.NinjaInterface;
import com.slimtrade.gui.buttons.BasicIconButton;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.components.StyledLabel;
import com.slimtrade.gui.components.ThemeLineBorder;
import com.slimtrade.gui.windows.BasicDialog;
import com.slimtrade.modules.saving.ISaveListener;
import com.slimtrade.modules.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;

public class NinjaWindow extends BasicDialog implements ISaveListener {

    private final JButton closeButton = new IconButton(DefaultIcon.CLOSE);
    private final JButton syncButton = new IconButton(DefaultIcon.ARROW_SYNC);
    private final JButton currencyButton = new BasicIconButton(() -> "/currency/Chaos_Orb.png");
    private final JButton essenceButton = new BasicIconButton(() -> "/currency/Deafening_Essence_of_Anguish.png");
    private final JButton scarabButton = new BasicIconButton(() -> "/currency/Essence_Scarab_Of_Ascent.png");
    private final JButton delveButton = new BasicIconButton(() -> "/currency/Aberrant_Fossil.png");
    private final JLabel syncLabel = new StyledLabel("Synced 2m ago").italic();

    public NinjaWindow() {
        // FIXME: Temp sync
        NinjaInterface.sync();
        setBackground(ThemeManager.TRANSPARENT);

        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(new ThemeLineBorder());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        buttonPanel.add(currencyButton);
        buttonPanel.add(essenceButton);
        buttonPanel.add(scarabButton);
        buttonPanel.add(delveButton);

        JPanel syncPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        syncPanel.add(syncLabel);
        syncPanel.add(Box.createHorizontalStrut(GUIReferences.HORIZONTAL_INSET_SMALL));
        syncPanel.add(syncButton);
        syncPanel.add(closeButton);
        syncButton.setEnabled(false);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.NORTH);
        southPanel.add(buttonPanel, BorderLayout.WEST);
        southPanel.add(syncPanel, BorderLayout.EAST);

        contentPanel.add(new NinjaFragmentsPanel(), BorderLayout.CENTER);
        contentPanel.add(southPanel, BorderLayout.SOUTH);

        pack();
        updateBounds();
        setVisible(true);
        SaveManager.stashSaveFile.addListener(this);
        addListeners();
    }

    private void addListeners() {
        closeButton.addActionListener(e -> setVisible(false));
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

}
