package com.slimtrade.core.saving;

import com.slimtrade.core.audio.Sound;
import com.slimtrade.core.audio.SoundComponent;

import javax.swing.*;
import java.lang.reflect.Field;

public class AudioSaveComponent implements SavableComponent {

    JComboBox<?> soundCombo;
    JSlider volumeSlider;
    String fieldName;
    Object saveFile;
    private Field field;

    public AudioSaveComponent(JComboBox<?> soundCombo, JSlider volumeSlider, String fieldName, Object saveFile) {
        this.soundCombo = soundCombo;
        this.volumeSlider = volumeSlider;
        this.fieldName = fieldName;
        this.saveFile = saveFile;
        try {
            this.field = saveFile.getClass().getField(fieldName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save() {
        SoundComponent soundComponent = new SoundComponent((Sound) soundCombo.getSelectedItem(), volumeSlider.getValue());
        try {
            field.set(saveFile, soundComponent);
//            SoundComponent soundComponent = (SoundComponent) field.get(saveFile);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void load() {
        try {
            SoundComponent soundComponent = (SoundComponent) field.get(saveFile);
            if(soundComponent == null) return;
            soundCombo.setSelectedItem(soundComponent.sound);
            volumeSlider.setValue(soundComponent.volume);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
//        SoundComponent soundComponent = new SoundComponent((Sound) soundCombo.getSelectedItem(), volumeSlider.getValue());

    }
}
