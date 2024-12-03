package com.slimtrade.gui.options.audio;

import com.slimtrade.core.audio.Sound;
import com.slimtrade.core.audio.SoundComponent;
import com.slimtrade.core.enums.DefaultIcon;
import com.slimtrade.core.utility.GUIReferences;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.components.GridBagGroup;
import com.slimtrade.gui.components.AudioComboBox;

import javax.swing.*;
import java.awt.*;

public class AudioRowControls extends GridBagGroup<SoundComponent> {

    private final JLabel titleLabel;
    public final AudioComboBox soundCombo = new AudioComboBox();
    public final JButton previewButton = new IconButton(DefaultIcon.PLAY);
    public final JSlider volumeSlider = new JSlider();
    public final JLabel volumeLabel = new JLabel();

    public AudioRowControls(String title) {
        titleLabel = new JLabel(title);
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
        parent.add(titleLabel, gc);
        gc.gridx++;
        parent.add(Box.createHorizontalStrut(GUIReferences.SMALL_INSET), gc);
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
