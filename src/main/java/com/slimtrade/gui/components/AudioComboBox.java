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
        if (AudioManager.getCustomFileCount() > 0) addItem(null);
        for (Sound sound : AudioManager.customSoundFiles) addItem(sound);
    }

}
