package github.zmilla93.gui.options.searching;

import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.utility.GUIReferences;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.components.ComponentPair;
import github.zmilla93.gui.components.ErrorLabel;
import github.zmilla93.gui.components.HotkeyButton;
import github.zmilla93.gui.components.PlaceholderTextField;
import github.zmilla93.gui.listening.TextChangeListener;
import github.zmilla93.modules.saving.ISavable;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;

public class StashSearchSettingsPanel extends JPanel implements ISavable {

    public final JButton newSearchGroupButton = new JButton("New Search Group");
    private final JTextField newSearchGroupNameInput = new PlaceholderTextField("Group Name...", 20);
    private final JLabel errorLabel = new ErrorLabel();
    private final JLabel windowHotkeyLabel = new JLabel("Window Hotkey");

    public final JComboBox<StashSearchWindowMode> modeCombo = new JComboBox<>();
    private final HotkeyButton hotkeyButton = new HotkeyButton();

    public StashSearchSettingsPanel() {
        setLayout(new GridBagLayout());

        for (StashSearchWindowMode mode : StashSearchWindowMode.values()) modeCombo.addItem(mode);
        JPanel newSearchGroupPanel = new ComponentPair(newSearchGroupButton, newSearchGroupNameInput);

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
        return ZUtil.cleanString(newSearchGroupNameInput.getText());
    }

    public void clearText() {
        newSearchGroupNameInput.setText("");
    }

    public void setError(String error) {
        errorLabel.setVisible(error != null);
        errorLabel.setText(error);
    }

    private void updateHotkeyButtonVisibility() {
        StashSearchWindowMode mode = (StashSearchWindowMode) modeCombo.getSelectedItem();
        boolean visible = mode == StashSearchWindowMode.COMBINED;
        hotkeyButton.setVisible(visible);
        windowHotkeyLabel.setVisible(visible);
    }

    @Override
    public void save() {
        SaveManager.settingsSaveFile.data.stashSearchWindowMode = (StashSearchWindowMode) modeCombo.getSelectedItem();
        SaveManager.settingsSaveFile.data.stashSearchHotkey = hotkeyButton.getData();
    }

    @Override
    public void load() {
        modeCombo.setSelectedItem(SaveManager.settingsSaveFile.data.stashSearchWindowMode);
        hotkeyButton.setData(SaveManager.settingsSaveFile.data.stashSearchHotkey);
        updateHotkeyButtonVisibility();
    }

}
