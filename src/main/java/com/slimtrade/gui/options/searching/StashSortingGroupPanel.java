package com.slimtrade.gui.options.searching;

import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.components.AddRemoveContainer;
import com.slimtrade.gui.components.AddRemovePanel;
import com.slimtrade.gui.components.HotkeyButton;
import com.slimtrade.gui.components.PlaceholderTextField;

import javax.swing.*;
import java.awt.*;

public class StashSortingGroupPanel extends AddRemovePanel {

    private final JButton removeButton = new IconButton("/icons/default/closex64.png");
    private final JButton shiftUpButton = new IconButton("/icons/default/arrow-upx48.png");
    private final JButton shiftDownButton = new IconButton("/icons/default/arrow-downx48.png");
    private String groupName;

    private final JButton renameButton = new JButton("Rename");
    private final JButton applyRenameButton = new JButton("Apply Name");
    private final JTextField renameInput = new PlaceholderTextField("Group Title...", 20);
    private final JButton newTermButton = new JButton("Add New Term");
    private final HotkeyButton hotkeyButton = new HotkeyButton();

    private final AddRemoveContainer<StashSortingTermPanel> termContainer = new AddRemoveContainer<>();

    // TODO : Window hotkey button
    public StashSortingGroupPanel(AddRemoveContainer parent, String name) {
        super(parent);
        setLayout(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();

        // Controls Panel
        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        controlsPanel.add(removeButton);
        controlsPanel.add(shiftDownButton);
        controlsPanel.add(shiftUpButton);
        controlsPanel.add(renameButton);
        controlsPanel.add(applyRenameButton);
        controlsPanel.add(renameInput);
        controlsPanel.add(newTermButton);

        // Main Panel
        gc = ZUtil.getGC();
        gc.anchor = GridBagConstraints.WEST;
        add(controlsPanel, gc);
        gc.gridy++;
//        add(newTermButton, gc);
//        gc.gridy++;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.BOTH;
        add(new JSeparator(JSeparator.HORIZONTAL), gc);
        gc.gridy++;
        gc.fill = GridBagConstraints.NONE;
        add(termContainer, gc);
        gc.gridy++;

        addListeners();
        showHideRename(false);
        updateGroupName(name);
        termContainer.add(new StashSortingTermPanel(termContainer));
    }

    public String getGroupName() {
        return groupName;
    }

    private void addListeners() {
        shiftUpButton.addActionListener(e -> shiftUp(shiftUpButton));
        shiftDownButton.addActionListener(e -> shiftDown(shiftDownButton));
        removeButton.addActionListener(e -> removeFromParent());
        newTermButton.addActionListener(e -> termContainer.add(new StashSortingTermPanel(termContainer)));
        renameButton.addActionListener(e -> showHideRename(true));
        applyRenameButton.addActionListener(e -> {
            showHideRename(false);
            updateGroupName(renameInput.getText());
        });
    }

    private void showHideRename(boolean show) {
        if (show) {
            renameButton.setVisible(false);
            applyRenameButton.setVisible(true);
            renameInput.setText(groupName);
            renameInput.setVisible(true);
        } else {
            renameButton.setVisible(true);
            applyRenameButton.setVisible(false);
            renameInput.setVisible(false);
        }
    }

    private void updateGroupName(String name) {
        name = name.trim().replaceAll("\s+", " ");
        if (name.equals("")) return;
        groupName = name;
        setBorder(BorderFactory.createTitledBorder(groupName));
    }

}
