package com.slimtrade.core.managers;

import com.slimtrade.core.audio.Sound;
import com.slimtrade.core.audio.SoundComponent;
import com.slimtrade.core.data.PriceThresholdData;
import com.slimtrade.core.enums.CurrencyType;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Handles playback of audio clips.
 */
public class AudioManager {

    // TODO : Should make clips cacheable, but current implementation leads to buggy playback under certain conditions
    private static final ArrayList<Sound> soundFiles = new ArrayList<>();
    public static final int MIN_VOLUME = -30;
    public static final int MAX_VOLUME = 6;
    public static final int RANGE = Math.abs(MIN_VOLUME) + MAX_VOLUME;
    private static int pingCount;
    private static int lootCount;
    private static int inbuiltCount;

    private static final HashMap<Sound, Clip> clipCache = new HashMap<>();
    private static final HashMap<Clip, AudioInputStream> streamCache = new HashMap<>();

    private static int customCount;

    public static void init() {
        rebuildSoundList();
    }

    public static ArrayList<Sound> getSoundFiles() {
        return soundFiles;
    }

    public static void rebuildSoundList() {
        clearCache();
        soundFiles.clear();
        addDefaultSoundFiles();
        addCustomSoundFiles();
    }

    private static void addDefaultSoundFiles() {
        soundFiles.add(new Sound("Ping 1", Sound.SoundType.INBUILT));
        soundFiles.add(new Sound("Ping 2", Sound.SoundType.INBUILT));
        soundFiles.add(new Sound("Blip 1", Sound.SoundType.INBUILT));
        soundFiles.add(new Sound("Blip 2", Sound.SoundType.INBUILT));
        soundFiles.add(new Sound("Blip 3", Sound.SoundType.INBUILT));
        pingCount = 5;

        soundFiles.add(new Sound("Loot 1", Sound.SoundType.INBUILT));
        soundFiles.add(new Sound("Loot 2", Sound.SoundType.INBUILT));
        soundFiles.add(new Sound("Loot 3", Sound.SoundType.INBUILT));
        soundFiles.add(new Sound("Loot 4", Sound.SoundType.INBUILT));
        soundFiles.add(new Sound("Loot 5", Sound.SoundType.INBUILT));
        soundFiles.add(new Sound("Loot 6", Sound.SoundType.INBUILT));
        soundFiles.add(new Sound("Loot 7", Sound.SoundType.INBUILT));
        soundFiles.add(new Sound("Loot 8", Sound.SoundType.INBUILT));
        soundFiles.add(new Sound("Loot 9", Sound.SoundType.INBUILT));
        lootCount = 9;

        inbuiltCount = pingCount + lootCount;
    }

    public static int getPingCount() {
        return pingCount;
    }

    public static int getInbuiltCount() {
        return inbuiltCount;
    }

    private static void addCustomSoundFiles() {
        customCount = 0;
        File audioDir = new File(SaveManager.getAudioDirectory());
        if (audioDir.exists()) {
            for (File file : Objects.requireNonNull(audioDir.listFiles())) {
                if (file.getName().endsWith(".wav") || file.getName().endsWith(".ogg")) {
                    soundFiles.add(new Sound(file.getName(), Sound.SoundType.CUSTOM));
                    customCount++;
                }
            }
        }
    }

    public static int indexOfSound(Sound sound) {
        return soundFiles.indexOf(sound);
    }

    public static int getCustomFileCount() {
        return customCount;
    }

    // Expected volume is 0 - 100
    public static void playSoundPercent(Sound sound, int volume) {
        if (volume == 0) return;
        playSoundRaw(sound, percentToRange(volume));
    }

    public static void playSoundComponent(SoundComponent soundComponent) {
        playSoundRaw(soundComponent.sound, percentToRange(soundComponent.volume));
    }

    // Expected volume is MIN_VOLUME - MAX_VOLUME
    private static void playSoundRaw(Sound sound, float volume) {
        if (volume <= MIN_VOLUME) {
            return;
        }
        if (volume > MAX_VOLUME) {
            volume = MAX_VOLUME;
        }
        Clip clip = getClip(sound);
        if (clip == null) return;
        FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        volumeControl.setValue(volume);
        clip.start();
    }

    private static Clip getClip(Sound sound) {
        AudioInputStream stream = null;
        try {
            Clip clip = AudioSystem.getClip();
            if (sound.soundType == Sound.SoundType.CUSTOM) {
                File file = new File(sound.getPath());
                if (!file.exists()) return null;
            }
            if (sound.soundType == Sound.SoundType.INBUILT) {
                stream = AudioSystem.getAudioInputStream(Objects.requireNonNull(AudioManager.class.getResource(sound.getPath())));
            } else {
                stream = AudioSystem.getAudioInputStream(sound.getURL());
            }
            clip.open(stream);
            final AudioInputStream finalStream = stream;
            clip.addLineListener(event -> {
                LineEvent.Type type = event.getType();
                if (type.equals(LineEvent.Type.STOP)) {
                    try {
                        finalStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    clip.stop();
                    clip.close();
                }
            });
            return clip;
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException ex) {
                    // Ignore
                }
            }
            return null;
        }
    }

    public static SoundComponent getPriceThresholdSound(String currencyType, int quantity) {
        CurrencyType currency = CurrencyType.getCurrencyType(currencyType);
        if (currency == null) return null;
        ArrayList<PriceThresholdData> thresholds = SaveManager.settingsSaveFile.data.priceThresholdMap.get(currency);
        if (thresholds == null) return null;
        for (PriceThresholdData data : thresholds) {
            if (quantity >= data.quantity) return data.soundComponent;
        }
        return null;
    }

    // Unimplemented, currently causes audio bugs
    private static Clip getCachedClip(Sound sound) {
        if (clipCache.containsKey(sound)) {
            return clipCache.get(sound);
        } else {
            AudioInputStream stream;
            try {
                Clip clip = AudioSystem.getClip();
                stream = AudioSystem.getAudioInputStream(sound.getURL());
                clip.open(stream);
                clip.addLineListener(event -> {
                    LineEvent.Type type = event.getType();
                    if (type.equals(LineEvent.Type.STOP)) {
                        clip.stop();
                    }
                });
                clipCache.put(sound, clip);
                streamCache.put(clip, stream);
                return clip;
            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static void clearCache() {
        for (Map.Entry<Sound, Clip> entry : clipCache.entrySet()) {
            Clip clip = entry.getValue();
            AudioInputStream stream = streamCache.get(clip);
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            clip.stop();
            clip.close();
        }
        clipCache.clear();
        streamCache.clear();
    }


    private static float percentToRange(int percent) {
        float f = MIN_VOLUME + (RANGE / (float) 100 * percent);
        return f;
    }

}
