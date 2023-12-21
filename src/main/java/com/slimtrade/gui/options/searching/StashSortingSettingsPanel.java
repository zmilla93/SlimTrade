package com.slimtrade.gui.options.searching;

import com.slimtrade.core.enums.Anchor;
import com.slimtrade.core.utility.GUIReferences;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.components.ComponentPair;
import com.slimtrade.gui.components.HotkeyButton;
import com.slimtrade.gui.components.PlaceholderTextField;

import javax.swing.*;
import java.awt.*;

public class StashSortingSettingsPanel extends JPanel {

    public final JButton newSearchGroupButton = new JButton("New Search Group");
    private PlaceholderTextField newSearchGroupNameInput = new PlaceholderTextField(20);

    private final JComboBox<StashSortingWindowMode> modeCombo = new JComboBox<>();
    private final JComboBox<Anchor> anchorCombo = new JComboBox<>();
    private final HotkeyButton hotkeyButton = new HotkeyButton();

    public StashSortingSettingsPanel() {
        setLayout(new GridBagLayout());

        newSearchGroupNameInput.setPlaceholderText("Group Name...");
        for (StashSortingWindowMode mode : StashSortingWindowMode.values()) modeCombo.addItem(mode);
        for (Anchor anchor : Anchor.values()) anchorCombo.addItem(anchor);

        GridBagConstraints gc = ZUtil.getGC();
        gc.anchor = GridBagConstraints.WEST;
        gc.weightx = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;

        // Controls Panel
        JPanel controlsPanel = new JPanel(new GridBagLayout());

        controlsPanel.add(new JLabel("Window Mode"), gc);
        gc.gridx++;
        gc.insets.left = GUIReferences.INSET;
        controlsPanel.add(modeCombo, gc);
        gc.insets.left = 0;
        gc.gridx = 0;
        gc.gridy++;

        controlsPanel.add(new JLabel("Window Hotkey"), gc);
        gc.gridx++;
        gc.insets.left = GUIReferences.INSET;
        controlsPanel.add(hotkeyButton, gc);
        gc.insets.left = 0;
        gc.gridx = 0;
        gc.gridy++;

//        controlsPanel.add(new JLabel("Window Anchor Corner"), gc);
//        gc.gridx++;
//        gc.insets.left = GUIReferences.INSET;
//        controlsPanel.add(anchorCombo, gc);
//        gc.insets.left = 0;
//        gc.gridx = 0;
//        gc.gridy++;

        JPanel newSearchGroupPanel = new ComponentPair(newSearchGroupButton, newSearchGroupNameInput);

        gc = ZUtil.getGC();
        gc.anchor = GridBagConstraints.WEST;
        gc.weightx = 1;
//        add(new PlainLabel("Search groups can be individual windows, or a combined window with a group selector."), gc);
//        gc.gridy++;
        add(controlsPanel, gc);
//        gc.gridy++;
//        add(hotkeyPanel, gc);
        gc.gridy++;
        add(newSearchGroupPanel, gc);

    }

    public String getNewSearchGroupName() {
        return newSearchGroupNameInput.getText();
    }

}
