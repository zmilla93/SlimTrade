package com.slimtrade.gui.components;

import com.slimtrade.core.audio.Sound;
import com.slimtrade.core.managers.AudioManager;

import javax.swing.*;

public class AudioComboBox extends JComboBox<Sound> {

    public AudioComboBox() {
        setRenderer(new AudioListCellRenderer());
        refresh();
    }

    public void refresh() {
        removeAllItems();
        for (Sound sound : AudioManager.pingSoundFiles) addItem(sound);
        addItem(null);
        for (Sound sound : AudioManager.lootSoundFiles) addItem(sound);
        if (AudioManager.getCustomSoundFileCount() > 0)
            addItem(null);
        for (Sound sound : AudioManager.customSoundFiles) addItem(sound);
    }

    // Separators are represented by null values, so don't allow them to be selected.
    @Override
    public void setSelectedItem(Object obj) {
        if (obj == null) return;
        super.setSelectedItem(obj);
    }

}
