package com.slimtrade.core.managers;

import com.slimtrade.core.audio.Sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AudioManager {

    // TODO : Should make clips cacheable, but current implementation leads to buggy playback under certain conditions
    private final ArrayList<Sound> soundFiles = new ArrayList<>();
    public static final int MIN_VOLUME = -30;
    public static final int MAX_VOLUME = 6;
    public static final int RANGE = Math.abs(MIN_VOLUME) + MAX_VOLUME;

    private final HashMap<Sound, Clip> clipCache = new HashMap<>();
    private final HashMap<Clip, AudioInputStream> streamCache = new HashMap<>();

    /**
     * Handles playback of audio clips.
     */
    public AudioManager() {
        rebuildSoundList();
    }

    public ArrayList<Sound> getSoundFiles(){
        return soundFiles;
    }

    public void rebuildSoundList() {
        clearCache();
        soundFiles.clear();
        addDefaultSoundFiles();
        addCustomSoundFiles();
    }

    private void addDefaultSoundFiles() {
        soundFiles.add(new Sound("Ping 1",  Sound.SoundType.INBUILT));
        soundFiles.add(new Sound("Ping 2",  Sound.SoundType.INBUILT));
        soundFiles.add(new Sound("Blip 1",  Sound.SoundType.INBUILT));
        soundFiles.add(new Sound("Blip 2",  Sound.SoundType.INBUILT));
        soundFiles.add(new Sound("Blip 3",  Sound.SoundType.INBUILT));
    }

    private void addCustomSoundFiles() {
        File audioDir = new File(SaveManager.getAudioDirectory());
        if (audioDir.exists()) {
            for (File file : Objects.requireNonNull(audioDir.listFiles())) {
                if (file.getName().endsWith(".wav")) {
                    soundFiles.add(new Sound(file.getName(),  Sound.SoundType.CUSTOM));
                }
            }
        }
    }

    public int indexOfSound(String name){
        for( int i = 0;i<soundFiles.size();i++){
           Sound sound = soundFiles.get(i);
           if(sound.name.equals(name)){
               return i;
           }
        }
        return -1;
    }

    // Expected volume is 0 - 100
    public void playSoundPercent(Sound sound, int volume) {
        playSoundRaw(sound, percentToRange(volume));
    }

    // Expected volume is MIN_VOLUME - MAX_VOLUME
    private void playSoundRaw(Sound sound, float volume) {
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

    private Clip getClip(Sound sound) {
        AudioInputStream stream;
        try {
            Clip clip = AudioSystem.getClip();
            if(sound.soundType == Sound.SoundType.CUSTOM){
                File file = new File(sound.getPath());
                if(!file.exists()) return null;
            }
            stream = AudioSystem.getAudioInputStream(sound.getURL());
            clip.open(stream);
            clip.addLineListener(event -> {
                LineEvent.Type type = event.getType();
                if (type.equals(LineEvent.Type.STOP)) {
                    try {
                        stream.close();
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
            return null;
        }
    }

    // Unimplemented, currently causes audio bugs
    private Clip getCachedClip(Sound sound) {
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
                        System.out.println("EOS!");
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

    public void clearCache() {
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

    private float percentToRange(int percent) {
        float f = MIN_VOLUME + (RANGE / (float) 100 * percent);
        return f;
    }

}
