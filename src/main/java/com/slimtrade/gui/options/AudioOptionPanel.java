package com.slimtrade.gui.options;

import com.slimtrade.App;
import com.slimtrade.core.audio.Sound;
import com.slimtrade.core.enums.DefaultIcon;
import com.slimtrade.core.managers.AudioManager;
import com.slimtrade.core.saving.AudioSaveComponent;
import com.slimtrade.core.utility.GUIReferences;
import com.slimtrade.gui.buttons.IconButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AudioOptionPanel extends AbstractOptionPanel {

    private int bottomInset = 1;

    JPanel innerPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gc = new GridBagConstraints();

    public AudioOptionPanel() {
//        setLayout(new BorderLayout());
//        contentPanel.setBackground(Color.ORANGE);
        contentPanel.setLayout(new BorderLayout());
        gc.gridx = 0;
        gc.gridy = 0;
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1;
//        setBackground(Color.RED);
        contentPanel.add(innerPanel, BorderLayout.WEST);
        addRow("Incoming Trade", "incomingSound");
        addRow("Outgoing Trade", "outgoingSound");
        addRow("Chat Scanner", "chatScannerSound");
        addRow("Player Joined Area", "playerJoinedAreaSound");
        addRow("Update Alert", "updateSound");
    }

    private void addRow(String title, String saveFieldName) {
        gc.insets = new Insets(0, GUIReferences.INSET, 2, GUIReferences.INSET);

        JButton previewButton = new IconButton(DefaultIcon.PLAY.path);
        JComboBox<Sound> soundCombo = new JComboBox<Sound>();
        JSlider volumeSlider = new JSlider();
        for (Sound sound : App.audioManager.soundfiles)
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

        AudioSaveComponent audioSaveComponent = new AudioSaveComponent(soundCombo, volumeSlider, saveFieldName, App.saveManager.settingsSaveFile);
        App.saveManager.registerAudioRow(audioSaveComponent);
        previewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(volumeSlider.getValue());
                App.audioManager.playSoundRaw((Sound) soundCombo.getSelectedItem(), AudioManager.percentToRange(volumeSlider.getValue()));
            }
        });

    }
}
