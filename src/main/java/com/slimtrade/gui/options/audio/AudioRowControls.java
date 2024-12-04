package com.slimtrade.gui.options.audio;

import com.slimtrade.core.audio.Sound;
import com.slimtrade.core.audio.SoundComponent;
import com.slimtrade.core.enums.DefaultIcon;
import com.slimtrade.core.managers.AudioManager;
import com.slimtrade.core.utility.GUIReferences;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.components.AudioComboBox;
import com.slimtrade.gui.components.GridBagGroup;

import javax.swing.*;
import java.awt.*;

public class AudioRowControls extends GridBagGroup<SoundComponent> {

    private final JLabel titleLabel;
    public final AudioComboBox soundCombo = new AudioComboBox();
    public final JButton previewButton = new IconButton(DefaultIcon.PLAY);
    public final JSlider volumeSlider = new JSlider();
    public final JLabel volumeLabel = new JLabel();

    public AudioRowControls(String title, JPanel parent, GridBagConstraints gc) {
        titleLabel = new JLabel(title);
        previewButton.addActionListener(e -> AudioManager.playSoundPercent((Sound) soundCombo.getSelectedItem(), volumeSlider.getValue()));
        volumeSlider.addChangeListener(e -> updateVolumeSlider(volumeSlider, volumeLabel));
        updateVolumeSlider(volumeSlider, volumeLabel);
        addToParent(parent, gc);
    }

    private void updateVolumeSlider(JSlider volumeSlider, JLabel volumeLabel) {
        volumeLabel.setText(ZUtil.getVolumeText(volumeSlider.getValue()));
    }

    @Override
    public SoundComponent getData() {
        Sound sound = (Sound) soundCombo.getSelectedItem();
        int volume = volumeSlider.getValue();
        return new SoundComponent(sound, volume);
    }

    @Override
    public void setData(SoundComponent soundComponent) {
        soundCombo.setSelectedItem(soundComponent.sound);
        volumeSlider.setValue(soundComponent.volume);
    }

    @Override
    public void addToParent(JComponent parent, GridBagConstraints gc) {
        gc.insets.right = GUIReferences.SMALL_INSET;
        parent.add(titleLabel, gc);
        gc.insets.right = 0;
        gc.gridx++;
        parent.add(soundCombo, gc);
        gc.gridx++;
        parent.add(previewButton, gc);
        gc.gridx++;
        parent.add(volumeSlider, gc);
        gc.gridx++;
        parent.add(volumeLabel, gc);
        gc.gridx = 0;
        gc.gridy++;
    }

}
