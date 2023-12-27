package com.slimtrade.gui.options.searching;

import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.components.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StashSortingGroupPanel extends AddRemovePanel {

    private final JButton removeButton = new IconButton("/icons/default/closex64.png");
    private final JButton shiftUpButton = new IconButton("/icons/default/arrow-upx48.png");
    private final JButton shiftDownButton = new IconButton("/icons/default/arrow-downx48.png");
    private String groupName;
    private String savedGroupName;
    private boolean showRename;

    private final JButton renameButton = new JButton("Rename");
    private final JButton applyRenameButton = new JButton("Apply Name");
    private final JTextField renameInput = new PlaceholderTextField("Group Title...", 20);
    private final JButton newTermButton = new JButton("Add New Term");
    private final HotkeyButton hotkeyButton = new HotkeyButton();

    private final AddRemoveContainer<StashSortingTermPanel> termContainer = new AddRemoveContainer<>();
    private final StashSortingOptionPanel optionPanel;

    // TODO : Window hotkey button
    public StashSortingGroupPanel(AddRemoveContainer<StashSortingGroupPanel> parent, StashSortingOptionPanel optionPanel, String name) {
        super(parent);
        this.optionPanel = optionPanel;
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
        controlsPanel.add(new ButtonWrapper(hotkeyButton));

        // Main Panel
        gc.anchor = GridBagConstraints.WEST;
        add(controlsPanel, gc);
        gc.gridy++;
//        add(newTermButton, gc);
//        gc.gridy++;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.BOTH;
        int INSET_SIZE = 2;
        gc.insets = new Insets(INSET_SIZE, 0, INSET_SIZE, 0);
        add(new JSeparator(JSeparator.HORIZONTAL), gc);
        gc.insets = new Insets(0, 0, 0, 0);
        gc.gridy++;
        gc.fill = GridBagConstraints.NONE;
        add(termContainer, gc);
        gc.gridy++;

        addListeners();
        showHideRename(false);
        updateGroupName(name);
        updateSavedGroupName();
        boolean showHotkeyButton = optionPanel.settingsPanel.modeCombo.getSelectedItem() == StashSortingWindowMode.SEPARATE;
        updateHotkeyVisibility(showHotkeyButton);
        termContainer.add(new StashSortingTermPanel(termContainer));
        termContainer.add(new StashSortingTermPanel(termContainer));
    }

    public StashSortingGroupPanel(AddRemoveContainer<StashSortingGroupPanel> parent, StashSortingOptionPanel optionPanel, StashSearchGroupData groupData, boolean hotkeyVisibility) {
        this(parent, optionPanel, groupData.title());
        hotkeyButton.setData(groupData.hotkeyData());
        termContainer.removeAll();
        for (StashSearchTermData termData : groupData.terms()) {
            termContainer.add(new StashSortingTermPanel(termContainer, termData));
        }
        updateHotkeyVisibility(hotkeyVisibility);
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
        showRename = show;
        if (show) {
            renameButton.setVisible(false);
            applyRenameButton.setVisible(true);
            renameInput.setText(groupName);
            renameInput.setVisible(true);
            renameInput.requestFocus();
        } else {
            renameButton.setVisible(true);
            applyRenameButton.setVisible(false);
            renameInput.setVisible(false);
        }
    }

    public String getGroupName() {
        return groupName;
    }

    public String getSavedGroupName() {
        return savedGroupName;
    }

    public void updateHotkeyVisibility(boolean visible) {
        hotkeyButton.setVisible(visible);
    }

    private void updateGroupName(String name) {
        name = name.trim().replaceAll("\s+", " ");
        if (name.equals("")) return;
        if (optionPanel.isDuplicateName(name)) return;
        groupName = name;
        setBorder(BorderFactory.createTitledBorder(groupName));
    }

    public void updateSavedGroupName() {
        this.savedGroupName = groupName;
    }

    protected void applyPendingGroupRename() {
        if (!showRename) return;
        showHideRename(false);
        updateGroupName(renameInput.getText());
    }

    public StashSearchGroupData getData() {
        ArrayList<StashSearchTermData> termList = new ArrayList<>();
        for (StashSortingTermPanel termPanel : termContainer.getComponentsTyped()) {
            termList.add(termPanel.getData());
        }
        StashSearchTermData[] terms = termList.toArray(new StashSearchTermData[0]);
        return new StashSearchGroupData(groupName, hotkeyButton.getData(), terms);
    }

}
