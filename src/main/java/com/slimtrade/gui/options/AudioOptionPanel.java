package com.slimtrade.gui.options;

import com.slimtrade.App;
import com.slimtrade.core.audio.Sound;
import com.slimtrade.core.audio.SoundComponent;
import com.slimtrade.core.enums.DefaultIcon;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.GUIReferences;
import com.slimtrade.gui.basic.ColorLabel;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.modules.saving.ISavable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AudioOptionPanel extends AbstractOptionPanel implements ISavable {

    private int bottomInset = 1;

    private JPanel innerPanel = new JPanel(new GridBagLayout());
    private GridBagConstraints gc = new GridBagConstraints();

    private ArrayList<JComboBox<Sound>> comboList = new ArrayList<>(5);
    private ArrayList<JSlider> sliderList = new ArrayList<>(5);
    private ArrayList<String> audioFiles = new ArrayList<>();

    private JLabel customAudioLabel = new JLabel();

    public AudioOptionPanel() {
        gc.gridx = 0;
        gc.gridy = 0;
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1;
        addRow("Incoming Trade", "incomingSound");
        addRow("Outgoing Trade", "outgoingSound");
        addRow("Chat Scanner", "chatScannerSound");
        addRow("Player Joined Area", "playerJoinedAreaSound");
        addRow("Update Alert", "updateSound");

        JPanel customButtons = new JPanel(new FlowLayout());
        JButton openFolderButton = new JButton("Open Audio Folder");
        JButton refreshButton = new JButton("Refresh");
        customButtons.add(openFolderButton);
        customButtons.add(refreshButton);

        addHeader("Sound Settings");
        addPanel(innerPanel);
        addPanel(Box.createVerticalStrut(GUIReferences.INSET));
        addHeader("Custom Audio");
        addPanel(new JLabel("Add audio files to the audio folder, then refresh."));
        ColorLabel label = new ColorLabel("Only supports .wav files.");
        label.bold = true;
        addPanel(label);
        addPanel(customButtons);
        addPanel(customAudioLabel);

        openFolderButton.addActionListener(e -> {
            File file = new File(SaveManager.getAudioDirectory());
            try {
                Desktop.getDesktop().open(file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                App.audioManager.clearCache();
                App.audioManager.rebuildSoundList();
                updateInfoLabel();
                refreshCombos();
                load();
            }
        });

//        App.saveManager.registerSavable(this);

    }

    private void addRow(String title, String saveFieldName) {
        gc.insets = new Insets(0, GUIReferences.INSET, 2, GUIReferences.INSET);
        JButton previewButton = new IconButton(DefaultIcon.PLAY.path);
        JComboBox<Sound> soundCombo = new JComboBox<>();
        JSlider volumeSlider = new JSlider();
        comboList.add(soundCombo);
        sliderList.add(volumeSlider);
        for (Sound sound : App.audioManager.getSoundFiles())
            soundCombo.addItem(sound);
        innerPanel.add(new JLabel(title), gc);
        gc.gridx++;
        gc.insets = new Insets(0, 0, 2, 0);
        innerPanel.add(previewButton, gc);
        gc.gridx++;
        innerPanel.add(volumeSlider, gc);
        gc.gridx++;
        innerPanel.add(soundCombo, gc);

        gc.gridx = 0;
        gc.gridy++;

        updateInfoLabel();
        previewButton.addActionListener(e -> {
            App.audioManager.playSoundPercent((Sound) soundCombo.getSelectedItem(), volumeSlider.getValue());
        });
    }

    private void refreshCombos() {
        for (JComboBox<Sound> combo : comboList) {
            combo.removeAllItems();
            for (Sound sound : App.audioManager.getSoundFiles()) {
                combo.addItem(sound);
            }
        }
    }

    private SoundComponent getAudioRow(int index) {
        return new SoundComponent((Sound) comboList.get(index).getSelectedItem(), sliderList.get(index).getValue());
    }

    private void setAudioRow(int index, SoundComponent row) {
        if (row == null || row.sound == null) return;
        int soundIndex = App.audioManager.indexOfSound(row.sound.name);
        comboList.get(index).setSelectedIndex(soundIndex);
        sliderList.get(index).setValue(row.volume);
    }

    public void updateInfoLabel(){
        int count = App.audioManager.getCustomFileCount();
        if(count == 0){
            customAudioLabel.setText("No custom files loaded.");
        }
        else if (count == 1){
            customAudioLabel.setText("1 custom file loaded.");
        }else{
            customAudioLabel.setText(count + " custom files loaded.");
        }
    }

    @Override
    public void save() {
        SaveManager.settingsSaveFile.data.incomingSound = getAudioRow(0);
        SaveManager.settingsSaveFile.data.outgoingSound = getAudioRow(1);
        SaveManager.settingsSaveFile.data.chatScannerSound = getAudioRow(2);
        SaveManager.settingsSaveFile.data.playerJoinedAreaSound = getAudioRow(3);
        SaveManager.settingsSaveFile.data.updateSound = getAudioRow(4);
    }

    @Override
    public void load() {
        refreshCombos();
        // FIXME:
        if (App.saveManager.settingsSaveFile == null) return;
        setAudioRow(0, SaveManager.settingsSaveFile.data.incomingSound);
        setAudioRow(1, SaveManager.settingsSaveFile.data.outgoingSound);
        setAudioRow(2, SaveManager.settingsSaveFile.data.chatScannerSound);
        setAudioRow(3, SaveManager.settingsSaveFile.data.playerJoinedAreaSound);
        setAudioRow(4, SaveManager.settingsSaveFile.data.updateSound);
    }

}
