package com.slimtrade.core.audio;

import com.slimtrade.App;
import com.slimtrade.core.saving.elements.SoundElement;

import javax.sound.sampled.*;
import java.io.IOException;

public class AudioManager {

    public static final int MIN_VOLUME = -30;
    public static final int MAX_VOLUME = 6;
    public static final int RANGE = Math.abs(MIN_VOLUME) + MAX_VOLUME;

    public void playSound(SoundElement component) {
        playSoundRaw(component.sound, component.getVolumeF());
    }

    public void playSoundRaw(Sound sound, float volume) {
        if (volume <= AudioManager.MIN_VOLUME) {
            return;
        }
        if (volume > MAX_VOLUME) {
            volume = MAX_VOLUME;
        }
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
            AudioInputStream stream = AudioSystem.getAudioInputStream(sound.getURL());
            clip.open(stream);
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(volume);
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            App.debugger.log("[SlimTrade] Issue retrieving audio file : " + e.getMessage());
        }
        assert clip != null;
        clip.start();
        Clip finalClip = clip;
        clip.addLineListener(event -> {
            LineEvent.Type type = event.getType();
            if (type.equals(LineEvent.Type.STOP)) {
                event.getLine();
                finalClip.stop();
                finalClip.close();
            }
        });
    }

}
