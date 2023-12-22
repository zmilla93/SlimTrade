package com.slimtrade.gui.options.searching;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.GUIReferences;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.components.ComponentPair;
import com.slimtrade.gui.components.HotkeyButton;
import com.slimtrade.gui.components.PlaceholderTextField;
import com.slimtrade.gui.components.ThemedStyleLabel;
import com.slimtrade.gui.listening.TextChangeListener;
import com.slimtrade.modules.saving.ISavable;
import com.slimtrade.modules.theme.ThemeColorVariant;
import com.slimtrade.modules.theme.ThemeColorVariantSetting;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;

public class StashSortingSettingsPanel extends JPanel implements ISavable {

    public final JButton newSearchGroupButton = new JButton("New Search Group");
    private final JTextField newSearchGroupNameInput = new PlaceholderTextField("Group Name...", 20);
    private final ThemedStyleLabel errorLabel = new ThemedStyleLabel();
    private final JLabel windowHotkeyLabel = new JLabel("Window Hotkey");

    public final JComboBox<StashSortingWindowMode> modeCombo = new JComboBox<>();
    private final HotkeyButton hotkeyButton = new HotkeyButton();

    public StashSortingSettingsPanel() {
        setLayout(new GridBagLayout());

        for (StashSortingWindowMode mode : StashSortingWindowMode.values()) modeCombo.addItem(mode);
        JPanel newSearchGroupPanel = new ComponentPair(newSearchGroupButton, newSearchGroupNameInput);
        errorLabel.setBold(true);
        errorLabel.setColor(new ThemeColorVariantSetting(ThemeColorVariant.RED, true));

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

        controlsPanel.add(windowHotkeyLabel, gc);
        gc.gridx++;
        gc.insets.left = GUIReferences.INSET;
        controlsPanel.add(hotkeyButton, gc);
        gc.insets.left = 0;
        gc.gridx = 0;
        gc.gridy++;

        // Main Panel
        gc = ZUtil.getGC();
        gc.anchor = GridBagConstraints.WEST;
        gc.weightx = 1;
        add(controlsPanel, gc);
        gc.gridy++;
        add(newSearchGroupPanel, gc);
        gc.gridy++;
        add(Box.createVerticalStrut(GUIReferences.SMALL_INSET), gc);
        gc.gridy++;
        add(errorLabel, gc);
        gc.gridy++;

        addListeners();
    }

    private void addListeners() {
        newSearchGroupNameInput.getDocument().addDocumentListener(new TextChangeListener() {
            @Override
            public void onTextChange(DocumentEvent e) {
                setError(null);
            }
        });
        modeCombo.addActionListener(e -> updateHotkeyButtonVisibility());
    }

    public String getNewSearchGroupName() {
        return newSearchGroupNameInput.getText().trim().replaceAll("\s+", " ");
    }

    public void clearText() {
        newSearchGroupNameInput.setText("");
    }

    public void setError(String error) {
        errorLabel.setVisible(error != null);
        errorLabel.setText(error);
    }

    private void updateHotkeyButtonVisibility() {
        StashSortingWindowMode mode = (StashSortingWindowMode) modeCombo.getSelectedItem();
        boolean visible = mode == StashSortingWindowMode.COMBINED;
        hotkeyButton.setVisible(visible);
        windowHotkeyLabel.setVisible(visible);
    }

    @Override
    public void save() {
        SaveManager.settingsSaveFile.data.stashSearchWindowMode = (StashSortingWindowMode) modeCombo.getSelectedItem();
        SaveManager.settingsSaveFile.data.stashSearchHotkey = hotkeyButton.getData();
    }

    @Override
    public void load() {
        modeCombo.setSelectedItem(SaveManager.settingsSaveFile.data.stashSearchWindowMode);
        hotkeyButton.setData(SaveManager.settingsSaveFile.data.stashSearchHotkey);
        updateHotkeyButtonVisibility();
    }

}
