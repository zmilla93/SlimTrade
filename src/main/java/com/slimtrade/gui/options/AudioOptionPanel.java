package com.slimtrade.gui.options;

import com.slimtrade.core.audio.Sound;
import com.slimtrade.core.enums.DefaultIcon;
import com.slimtrade.core.managers.AudioManager;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.GUIReferences;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.components.ButtonPanel;
import com.slimtrade.gui.components.LimitCombo;
import com.slimtrade.gui.components.StyledLabel;
import com.slimtrade.gui.options.audio.AudioRowControls;
import com.slimtrade.gui.options.audio.AudioThresholdPanel;
import com.slimtrade.modules.saving.ISavable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AudioOptionPanel extends AbstractOptionPanel implements ISavable {

    private final JPanel innerPanel = new JPanel(new GridBagLayout());
    private final GridBagConstraints gc = new GridBagConstraints();
    private final JButton openFolderButton = new JButton("Open Audio Folder");
    private final JButton refreshButton = new JButton("Refresh");

    private final JLabel customAudioLabel = new JLabel();
    private final AudioThresholdPanel audioThresholdPanel = new AudioThresholdPanel();

    // Controls
    private final ArrayList<AudioRowControls> controlList = new ArrayList<>();
    private final AudioRowControls incomingTradeControls;
    private final AudioRowControls outgoingTradeControls;
    private final AudioRowControls chatScannerControls;
    private final AudioRowControls playerJoinedAreaControls;
    private final AudioRowControls ignoredItemControls;
    private final AudioRowControls updateAlertControls;

    public AudioOptionPanel() {
        gc.gridx = 0;
        gc.gridy = 0;
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1;

        // Controls
        incomingTradeControls = addRow("Incoming Trade");
        outgoingTradeControls = addRow("Outgoing Trade");
        chatScannerControls = addRow("Chat Scanner");
        playerJoinedAreaControls = addRow("Player Joined Area");
        ignoredItemControls = addRow("Ignored Item");
        updateAlertControls = addRow("Update Alert");

        ButtonPanel customButtons = new ButtonPanel();

        customButtons.add(openFolderButton);
        customButtons.add(refreshButton);

        addHeader("Custom Audio");
        addComponent(new JLabel("Add audio files to the audio folder, then refresh. Custom files will then be available in all audio dropdowns."));
        JLabel label = new StyledLabel("Only supports .wav files. Online file converters are available if you have different formats.").bold();
        addComponent(label);
        addComponent(customButtons);
        addComponent(customAudioLabel);
        addVerticalStrut();

        addHeader("Sound Settings");
        addComponent(innerPanel);
        addVerticalStrut();

        addHeader("Price Thresholds");
        addComponent(audioThresholdPanel);

        updateInfoLabel();
        addListeners();
    }

    private void addListeners() {
        openFolderButton.addActionListener(e -> ZUtil.openExplorer(SaveManager.getAudioDirectory()));
        refreshButton.addActionListener(e -> {
            AudioManager.clearCache();
            AudioManager.rebuildSoundList();
            updateInfoLabel();
            refreshCombos();
            load();
        });
    }

    private AudioRowControls addRow(String title) {
        JButton previewButton = new IconButton(DefaultIcon.PLAY);
        JComboBox<Sound> soundCombo = new LimitCombo<>();
        JSlider volumeSlider = new JSlider();
        JLabel volumeLabel = new JLabel();
        for (Sound sound : AudioManager.getSoundFiles())
            soundCombo.addItem(sound);
        innerPanel.add(new JLabel(title), gc);
        gc.gridx++;
        innerPanel.add(Box.createHorizontalStrut(GUIReferences.SMALL_INSET), gc);
        gc.gridx++;
        innerPanel.add(soundCombo, gc);
        gc.gridx++;
        innerPanel.add(previewButton, gc);
        gc.gridx++;
        innerPanel.add(volumeSlider, gc);
        gc.gridx++;
        innerPanel.add(volumeLabel, gc);

        gc.gridx = 0;
        gc.gridy++;

        previewButton.addActionListener(e -> AudioManager.playSoundPercent((Sound) soundCombo.getSelectedItem(), volumeSlider.getValue()));
        volumeSlider.addChangeListener(e -> updateVolumeSlider(volumeSlider, volumeLabel));
        updateVolumeSlider(volumeSlider, volumeLabel);
        AudioRowControls controls = new AudioRowControls(soundCombo, volumeSlider);
        controlList.add(controls);
        return controls;
    }

    private void updateVolumeSlider(JSlider volumeSlider, JLabel volumeLabel) {
        volumeLabel.setText(ZUtil.getVolumeText(volumeSlider.getValue()));
    }

    private void refreshCombos() {
        for (AudioRowControls control : controlList) {
            JComboBox<Sound> combo = control.comboBox;
            combo.removeAllItems();
            for (Sound sound : AudioManager.getSoundFiles()) {
                combo.addItem(sound);
            }
        }
        audioThresholdPanel.refreshCombos();
    }

    public void updateInfoLabel() {
        int count = AudioManager.getCustomFileCount();
        if (count == 0) {
            customAudioLabel.setText("No custom files loaded.");
        } else if (count == 1) {
            customAudioLabel.setText("1 custom file loaded.");
        } else {
            customAudioLabel.setText(count + " custom files loaded.");
        }
    }

    @Override
    public void save() {
        SaveManager.settingsSaveFile.data.incomingSound = incomingTradeControls.getSoundComponent();
        SaveManager.settingsSaveFile.data.outgoingSound = outgoingTradeControls.getSoundComponent();
        SaveManager.settingsSaveFile.data.chatScannerSound = chatScannerControls.getSoundComponent();
        SaveManager.settingsSaveFile.data.playerJoinedAreaSound = playerJoinedAreaControls.getSoundComponent();
        SaveManager.settingsSaveFile.data.itemIgnoredSound = ignoredItemControls.getSoundComponent();
        SaveManager.settingsSaveFile.data.updateSound = updateAlertControls.getSoundComponent();
    }

    @Override
    public void load() {
        refreshCombos();
        incomingTradeControls.setSoundComponent(SaveManager.settingsSaveFile.data.incomingSound);
        outgoingTradeControls.setSoundComponent(SaveManager.settingsSaveFile.data.outgoingSound);
        chatScannerControls.setSoundComponent(SaveManager.settingsSaveFile.data.chatScannerSound);
        playerJoinedAreaControls.setSoundComponent(SaveManager.settingsSaveFile.data.playerJoinedAreaSound);
        ignoredItemControls.setSoundComponent(SaveManager.settingsSaveFile.data.itemIgnoredSound);
        updateAlertControls.setSoundComponent(SaveManager.settingsSaveFile.data.updateSound);
    }

}
