package com.slimtrade.gui.options.audio;

import com.slimtrade.core.audio.Sound;
import com.slimtrade.core.audio.SoundComponent;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class AudioRowControls {

    public final JComboBox<Sound> comboBox;
    public final JSlider slider;

    public AudioRowControls(JComboBox<Sound> comboBox, JSlider slider) {
        this.comboBox = comboBox;
        this.slider = slider;
    }

    public void setSoundComponent(@NotNull SoundComponent component) {
        comboBox.setSelectedItem(component.sound);
        slider.setValue(component.volume);
    }

    @NotNull
    public SoundComponent getSoundComponent() {
        Sound sound = (Sound) comboBox.getSelectedItem();
        int volume = slider.getValue();
        return new SoundComponent(sound, volume);
    }

}
