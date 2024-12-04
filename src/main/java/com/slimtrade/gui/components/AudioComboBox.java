package com.slimtrade.gui.components;

import com.slimtrade.core.audio.Sound;
import com.slimtrade.core.managers.AudioManager;

import javax.swing.*;
import java.util.ArrayList;

/**
 * A JComboBox that displays all loaded sound files.
 */
public class AudioComboBox extends JComboBox<Sound> {

    private static final ArrayList<AudioComboBox> comboBoxes = new ArrayList<>();

    public AudioComboBox() {
        comboBoxes.add(this);
        setRenderer(new AudioListCellRenderer());
        refreshSoundList();
    }

    public void refreshSoundList() {
        Sound currentSound = (Sound) getSelectedItem();
        removeAllItems();
        for (Sound sound : AudioManager.pingSoundFiles) addItem(sound);
        addItem(null);
        for (Sound sound : AudioManager.lootSoundFiles) addItem(sound);
        if (AudioManager.getCustomSoundFileCount() > 0)
            addItem(null);
        for (Sound sound : AudioManager.customSoundFiles) addItem(sound);
        setSelectedItem(currentSound);
    }

    /**
     * This should be called anytime the custom sound files change.
     */
    public static void refreshAllComboSoundLists() {
        for (AudioComboBox comboBox : comboBoxes) comboBox.refreshSoundList();
    }

    // FIXME: Technically combos should be removed anytime their are done being used, but
    //        manually removing during save & load is way simpler and almost as good.
    public static void purgeUnusedCombos() {
        for (AudioComboBox comboBox : comboBoxes)
            if (comboBox.getParent() == null) {
                comboBox.remove(comboBox);
            }
    }

    // Separators are represented by null values, so don't allow them to be selected.
    @Override
    public void setSelectedItem(Object obj) {
        if (obj == null) return;
        super.setSelectedItem(obj);
    }

}
