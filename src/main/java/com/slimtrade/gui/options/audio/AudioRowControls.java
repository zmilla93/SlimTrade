package com.slimtrade.gui.options.audio;

import com.slimtrade.core.audio.Sound;
import com.slimtrade.core.audio.SoundComponent;

import javax.swing.*;

public record AudioRowControls(JComboBox<Sound> comboBox, JSlider slider) {

    public void setSoundComponent(SoundComponent component) {
        this.comboBox.setSelectedItem(component.sound);
        this.slider.setValue(component.volume);
    }

    public SoundComponent getSoundComponent() {
        Sound sound = (Sound) comboBox.getSelectedItem();
        int volume = slider.getValue();
        return new SoundComponent(sound, volume);
    }

}
