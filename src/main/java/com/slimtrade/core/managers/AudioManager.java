package com.slimtrade.core.managers;

import com.slimtrade.core.audio.Sound;
import com.slimtrade.core.audio.SoundComponent;
import com.slimtrade.core.data.PriceThresholdData;
import com.slimtrade.core.enums.CurrencyType;
import com.slimtrade.modules.updater.ZLogger;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Handles loading and playback of audio clips.
 */
public class AudioManager {

    // List of all sound files
    public static final ArrayList<Sound> soundFiles = new ArrayList<>();

    // Categories of sound files for controller dividers
    public static final ArrayList<Sound> pingSoundFiles = new ArrayList<>();
    public static final ArrayList<Sound> lootSoundFiles = new ArrayList<>();
    public static final ArrayList<Sound> customSoundFiles = new ArrayList<>();

    // Volume Range
    public static final int MIN_VOLUME = -30;
    public static final int MAX_VOLUME = 6;
    public static final int RANGE = Math.abs(MIN_VOLUME) + MAX_VOLUME;

    public static void init() {
        rebuildSoundList();
    }

    /**
     * Plays a {@link SoundComponent}, which is a combination of a {@link Sound} and a volume between 0 and 100.
     *
     * @param soundComponent The {@link SoundComponent} to play
     */
    public static void playSoundComponent(SoundComponent soundComponent) {
        playSoundRaw(soundComponent.sound, percentToRange(soundComponent.volume));
    }

    /**
     * Plays a {@link Sound} at a volume between 0 and 100.
     *
     * @param sound  The target {@link Sound}
     * @param volume A volume between 0 and 100
     */
    public static void playSoundPercent(Sound sound, int volume) {
        if (volume == 0) return;
        playSoundRaw(sound, percentToRange(volume));
    }

    /**
     * Plays a {@link Sound} at a volume between MIN_VOLUME and MAX_VOLUME, which are arbitrary values that make for a nice volume range.
     *
     * @param sound  {@link Sound} to play
     * @param volume A volume between MIN_VOLUME and MAX_VOLUME
     */
    private static void playSoundRaw(Sound sound, float volume) {
        if (sound == null) return;
        if (volume <= MIN_VOLUME) return;
        if (volume > MAX_VOLUME) volume = MAX_VOLUME;
        Clip clip = getClip(sound);
        if (clip == null) return;
        FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        volumeControl.setValue(volume);
        clip.start();
    }

    /**
     * Given the name of a POE currency and a quantity, returns the highest price threshold that it exceeds or is equivalent to.
     *
     * @param currencyType The name of a POE Currency.
     * @param quantity     The quantity of currency.
     * @return A {@link SoundComponent}.
     */
    public static SoundComponent getPriceThresholdSound(String currencyType, int quantity) {
        CurrencyType currency = CurrencyType.getCurrencyType(currencyType);
        if (currency == null) return null;
        ArrayList<PriceThresholdData> thresholds = SaveManager.settingsSaveFile.data.priceThresholdMap.get(currency);
        if (thresholds == null) return null;
        for (PriceThresholdData data : thresholds) {
            if (data.soundComponent.sound == null) continue;
            if (quantity >= data.quantity) return data.soundComponent;
        }
        return null;
    }

    /**
     * Rebuilds the sound list using the latest audio files.
     * This should be called anytime the files in the audio folder change.
     */
    public static void rebuildSoundList() {
        soundFiles.clear();
        pingSoundFiles.clear();
        lootSoundFiles.clear();
        addDefaultSoundFiles();
        customSoundFiles.clear();
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

    public static int getCustomSoundFileCount() {
        return customSoundFiles.size();
    }

    /**
     * Adds all .wav files in the audio directory to the sound lists.
     */
    private static void addCustomSoundFiles() {
        File audioFolder = SaveManager.getAudioDirectory().toFile();
        if (!SaveManager.getAudioDirectory().toFile().exists()) return;
        for (File file : Objects.requireNonNull(audioFolder.listFiles())) {
            if (file.getName().endsWith(".wav")) {
                Sound sound = new Sound(file.getName(), Sound.SoundType.CUSTOM);
                soundFiles.add(sound);
                customSoundFiles.add(sound);
            }
        }

    }

    /**
     * Gets a playable audio clip using Java's audio system.
     *
     * @param sound The {@link Sound} to play
     * @return A java audio clip
     */
    private static Clip getClip(Sound sound) {
        AudioInputStream stream;
        try {
            Clip clip = AudioSystem.getClip();
            if (sound.soundType == Sound.SoundType.CUSTOM) {
                if (!sound.getPath().toFile().exists()) {
                    ZLogger.err("Audio file not found: " + sound.getDetails());
                    return null;
                }
            }
            stream = AudioSystem.getAudioInputStream(sound.getURL());
            clip.open(stream);
            final AudioInputStream finalStream = stream;
            clip.addLineListener(event -> {
                LineEvent.Type type = event.getType();
                if (type.equals(LineEvent.Type.STOP)) {
                    clip.stop();
                    clip.close();
                    try {
                        finalStream.close();
                    } catch (IOException e) {
                        ZLogger.err("Failed to close audio stream!");
                    }
                }
            });
            return clip;
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            ZLogger.err("Error when creating audio clip: " + sound.getDetails());
            return null;
        }
    }

    /**
     * Converts a percent 0-100 to the corresponding value between MIN_VOLUME and MAX_VOLUME.
     *
     * @param percent A number between 0 and 100.
     * @return A volume between MIN_VOLUME AND MAX_VOLUME.
     */
    private static float percentToRange(int percent) {
        return MIN_VOLUME + (RANGE / (float) 100 * percent);
    }

}
