package github.zmilla93.gui.components;

import github.zmilla93.core.audio.Sound;
import github.zmilla93.core.managers.AudioManager;

import java.util.ArrayList;

/**
 * A JComboBox that displays all loaded sound files.
 */
public class AudioComboBox extends SeparatedComboBox<Sound> {

    private static final ArrayList<AudioComboBox> comboBoxes = new ArrayList<>();

    public AudioComboBox() {
        comboBoxes.add(this);
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

}
