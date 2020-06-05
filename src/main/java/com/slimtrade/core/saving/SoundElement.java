package com.slimtrade.core.saving;

import com.slimtrade.core.audio.AudioManager;
import com.slimtrade.core.audio.Sound;

public class SoundElement {

    public Sound sound;
    public int volume;
    public float volumeF;


    public SoundElement(Sound sound, int volume) {
        this.sound = sound;
        this.volume = volume;
        this.volumeF = (float) AudioManager.MIN_VOLUME + ((float) AudioManager.RANGE / 100.0f * (float) this.volume);
    }

}
