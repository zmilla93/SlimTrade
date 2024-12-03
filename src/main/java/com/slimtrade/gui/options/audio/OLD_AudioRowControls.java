package com.slimtrade.gui.options.audio;

import com.slimtrade.core.audio.Sound;
import com.slimtrade.core.audio.SoundComponent;
import com.slimtrade.gui.components.AudioComboBox;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

@Deprecated
public class OLD_AudioRowControls {

    public final AudioComboBox comboBox;
    public final JSlider slider;

    public OLD_AudioRowControls(AudioComboBox comboBox, JSlider slider) {
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
