package com.slimtrade.core.saving.elements;

import com.slimtrade.core.audio.AudioManager;
import com.slimtrade.core.audio.Sound;

public class SoundElement {

    public Sound sound;
    public int volume;
    private int cachedVolume;
    private float volumeF;

    public SoundElement(Sound sound, int volume) {
        this.sound = sound;
        this.volume = volume;
    }

    public float getVolumeF() {
        if (volume != cachedVolume) {
            volumeF = (float) AudioManager.MIN_VOLUME + ((float) AudioManager.RANGE / 100.0f * (float) this.volume);
            cachedVolume = volume;
        }
        return volumeF;
    }

}
