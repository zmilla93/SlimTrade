package com.slimtrade.gui.options.searching;

import com.slimtrade.core.enums.DefaultIcon;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.components.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StashSearchGroupPanel extends AddRemovePanel {

    private final JButton removeButton = new IconButton(DefaultIcon.CLOSE);
    private final JButton shiftUpButton = new IconButton(DefaultIcon.ARROW_UP);
    private final JButton shiftDownButton = new IconButton(DefaultIcon.ARROW_DOWN);
    private final int id;
    private String groupName;
    private String savedGroupName;
    private boolean showRename;

    private final JButton renameButton = new JButton("Rename");
    private final JButton applyRenameButton = new JButton("Apply Name");
    private final JTextField renameInput = new PlaceholderTextField("Group Title...", 20);
    private final JButton newTermButton = new JButton("Add New Term");
    private final HotkeyButton hotkeyButton = new HotkeyButton();

    private final AddRemoveContainer<StashSearchTermPanel> termContainer = new AddRemoveContainer<>();
    private final StashSearchOptionPanel optionPanel;

    // TODO : Window hotkey button
    public StashSearchGroupPanel(AddRemoveContainer<StashSearchGroupPanel> parent, StashSearchOptionPanel optionPanel, String name) {
        this(parent, optionPanel, name, -1);
    }

    public StashSearchGroupPanel(AddRemoveContainer<StashSearchGroupPanel> parent, StashSearchOptionPanel optionPanel, String name, int id) {
        super(parent);
        this.optionPanel = optionPanel;
        this.id = id == -1 ? optionPanel.getNextId() : id;

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
        boolean showHotkeyButton = optionPanel.settingsPanel.modeCombo.getSelectedItem() == StashSearchWindowMode.SEPARATE;
        updateHotkeyVisibility(showHotkeyButton);
        termContainer.add(new StashSearchTermPanel(termContainer));
        termContainer.add(new StashSearchTermPanel(termContainer));
    }

    public StashSearchGroupPanel(AddRemoveContainer<StashSearchGroupPanel> parent, StashSearchOptionPanel optionPanel, StashSearchGroupData groupData, boolean hotkeyVisibility) {
        this(parent, optionPanel, groupData.title, groupData.id);
        hotkeyButton.setData(groupData.hotkeyData);
        termContainer.removeAll();
        for (StashSearchTermData termData : groupData.terms) {
            termContainer.add(new StashSearchTermPanel(termContainer, termData));
        }
        updateHotkeyVisibility(hotkeyVisibility);
    }

    private void addListeners() {
        shiftUpButton.addActionListener(e -> shiftUp(shiftUpButton));
        shiftDownButton.addActionListener(e -> shiftDown(shiftDownButton));
        removeButton.addActionListener(e -> {
            optionPanel.addIdToReset(id);
            removeFromParent();
        });
        newTermButton.addActionListener(e -> termContainer.add(new StashSearchTermPanel(termContainer)));
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
        name = ZUtil.cleanString(name);
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
        ArrayList<StashSearchTermData> terms = new ArrayList<>();
        for (StashSearchTermPanel termPanel : termContainer.getComponentsTyped()) {
            terms.add(termPanel.getData());
        }
        return new StashSearchGroupData(id, groupName, hotkeyButton.getData(), terms);
    }

}
