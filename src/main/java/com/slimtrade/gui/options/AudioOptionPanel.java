package com.slimtrade.gui.options;

import com.slimtrade.core.audio.Sound;
import com.slimtrade.core.audio.SoundComponent;
import com.slimtrade.core.enums.DefaultIcon;
import com.slimtrade.core.managers.AudioManager;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.GUIReferences;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.basic.ColorLabel;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.components.ButtonPanel;
import com.slimtrade.gui.components.LimitCombo;
import com.slimtrade.gui.components.PlainLabel;
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

    private final ArrayList<JComboBox<Sound>> comboList = new ArrayList<>(6);
    private final ArrayList<JSlider> sliderList = new ArrayList<>(6);

    private final JLabel customAudioLabel = new JLabel();
    private final AudioThresholdPanel audioThresholdPanel = new AudioThresholdPanel();

    public AudioOptionPanel() {
        gc.gridx = 0;
        gc.gridy = 0;
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1;

        // FIXME: (Minor) Should remove order dependency.
        // IMPORTANT : These must be in the same order as the save and load functions!
        addRow("Incoming Trade");
        addRow("Outgoing Trade");
        addRow("Chat Scanner");
        addRow("Player Joined Area");
        addRow("Ignored Item");
        addRow("Update Alert");

        ButtonPanel customButtons = new ButtonPanel();

        customButtons.add(openFolderButton);
        customButtons.add(refreshButton);

        addHeader("Sound Settings");
        addComponent(innerPanel);
        addComponent(Box.createVerticalStrut(GUIReferences.INSET));
        addHeader("Custom Audio");
        addComponent(new PlainLabel("Add audio files to the audio folder, then refresh. Custom files will then be available in all audio dropdowns."));
        ColorLabel label = new ColorLabel("Only supports .wav files. Online file converters are available if you have different formats.");
        label.bold = true;
        addComponent(label);
        addComponent(customButtons);
        addComponent(customAudioLabel);

        addVerticalStrut();
        addHeader("Price Thresholds");
        addComponent(audioThresholdPanel);

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

    private void addRow(String title) {
        JButton previewButton = new IconButton(DefaultIcon.PLAY.path);
        JComboBox<Sound> soundCombo = new LimitCombo<>();
        JSlider volumeSlider = new JSlider();
        comboList.add(soundCombo);
        sliderList.add(volumeSlider);
        for (Sound sound : AudioManager.getSoundFiles())
            soundCombo.addItem(sound);
        innerPanel.add(new JLabel(title), gc);
        gc.gridx++;
        innerPanel.add(previewButton, gc);
        gc.gridx++;
        innerPanel.add(volumeSlider, gc);
        gc.gridx++;
        innerPanel.add(soundCombo, gc);

        gc.gridx = 0;
        gc.gridy++;

        updateInfoLabel();
        previewButton.addActionListener(e -> AudioManager.playSoundPercent((Sound) soundCombo.getSelectedItem(), volumeSlider.getValue()));
    }

    private void refreshCombos() {
        for (JComboBox<Sound> combo : comboList) {
            combo.removeAllItems();
            for (Sound sound : AudioManager.getSoundFiles()) {
                combo.addItem(sound);
            }
        }
        audioThresholdPanel.refreshCombos();
    }

    private SoundComponent getAudioRow(int index) {
        return new SoundComponent((Sound) comboList.get(index).getSelectedItem(), sliderList.get(index).getValue());
    }

    private void setAudioRow(int index, SoundComponent row) {
        if (row == null || row.sound == null) return;
        int soundIndex = AudioManager.indexOfSound(row.sound);
        comboList.get(index).setSelectedIndex(soundIndex);
        sliderList.get(index).setValue(row.volume);
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
        SaveManager.settingsSaveFile.data.incomingSound = getAudioRow(0);
        SaveManager.settingsSaveFile.data.outgoingSound = getAudioRow(1);
        SaveManager.settingsSaveFile.data.chatScannerSound = getAudioRow(2);
        SaveManager.settingsSaveFile.data.playerJoinedAreaSound = getAudioRow(3);
        SaveManager.settingsSaveFile.data.itemIgnoredSound = getAudioRow(4);
        SaveManager.settingsSaveFile.data.updateSound = getAudioRow(5);
    }

    @Override
    public void load() {
        refreshCombos();
        setAudioRow(0, SaveManager.settingsSaveFile.data.incomingSound);
        setAudioRow(1, SaveManager.settingsSaveFile.data.outgoingSound);
        setAudioRow(2, SaveManager.settingsSaveFile.data.chatScannerSound);
        setAudioRow(3, SaveManager.settingsSaveFile.data.playerJoinedAreaSound);
        setAudioRow(4, SaveManager.settingsSaveFile.data.itemIgnoredSound);
        setAudioRow(5, SaveManager.settingsSaveFile.data.updateSound);
    }

}
