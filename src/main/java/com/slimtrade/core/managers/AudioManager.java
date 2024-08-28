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
    public static final ArrayList<Sound> soundFiles = new ArrayList<>();
    public static final ArrayList<Sound> pingSoundFiles = new ArrayList<>();
    public static final ArrayList<Sound> lootSoundFiles = new ArrayList<>();
    public static final ArrayList<Sound> customSoundFiles = new ArrayList<>();


    public static final int MIN_VOLUME = -30;
    public static final int MAX_VOLUME = 6;
    public static final int RANGE = Math.abs(MIN_VOLUME) + MAX_VOLUME;

    private static final HashMap<Sound, Clip> clipCache = new HashMap<>();
    private static final HashMap<Clip, AudioInputStream> streamCache = new HashMap<>();

    private static int customCount;

    public static void init() {
        rebuildSoundList();
    }

    public static void rebuildSoundList() {
        clearCache();
        soundFiles.clear();
        addDefaultSoundFiles();
        addCustomSoundFiles();
    }

    private static void addDefaultSoundFiles() {
        addPingSound("Ping 1");
        addPingSound("Ping 2");
        addPingSound("Ping 3");
        addPingSound("Blip 1");
        addPingSound("Blip 2");
        addPingSound("Blip 3");

        addLootSound("Loot 1");
        addLootSound("Loot 2");
        addLootSound("Loot 3");
        addLootSound("Loot 4");
        addLootSound("Loot 5");
        addLootSound("Loot 6");
        addLootSound("Loot 7");
        addLootSound("Loot 8");
        addLootSound("Loot 9");
    }

    private static void addPingSound(String name) {
        Sound sound = new Sound(name, Sound.SoundType.INBUILT);
        pingSoundFiles.add(sound);
        addSoundMutual(sound);
    }

    private static void addLootSound(String name) {
        Sound sound = new Sound(name, Sound.SoundType.INBUILT);
        lootSoundFiles.add(sound);
        addSoundMutual(sound);
    }

    private static void addSoundMutual(Sound sound) {
        soundFiles.add(sound);
    }

    public static int indexOfSound(Sound sound) {
        return soundFiles.indexOf(sound);
    }

    public static int getCustomFileCount() {
        return customCount;
    }

    private static void addCustomSoundFiles() {
        customCount = 0;
        File audioDir = new File(SaveManager.getAudioDirectory());
        if (audioDir.exists()) {
            for (File file : Objects.requireNonNull(audioDir.listFiles())) {
                if (file.getName().endsWith(".wav")) {
                    Sound sound = new Sound(file.getName(), Sound.SoundType.CUSTOM);
                    soundFiles.add(sound);
                    customSoundFiles.add(sound);
                    customCount++;
                }
            }
        }
    }

    public static void playSoundComponent(SoundComponent soundComponent) {
        playSoundRaw(soundComponent.sound, percentToRange(soundComponent.volume));
    }

    // Expected volume is 0 and 100
    public static void playSoundPercent(Sound sound, int volume) {
        if (volume == 0) return;
        playSoundRaw(sound, percentToRange(volume));
    }

    // Expected volume is between MIN_VOLUME and MAX_VOLUME
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
