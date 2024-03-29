package com.slimtrade.core.audio;

import com.slimtrade.core.managers.SaveManager;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class Sound {

    public enum SoundType {INBUILT, CUSTOM}

    public final String name;
    public SoundType soundType;
    private transient String path;
    private transient URL url;

    public Sound(String name, SoundType soundType) {
        this.soundType = soundType;
        if (soundType == SoundType.INBUILT) {
            this.name = name;
        } else if (soundType == SoundType.CUSTOM) {
            this.name = name.replaceFirst("\\.wav\\Z", "");
            path = SaveManager.getAudioDirectory() + name;
        } else {
            this.name = "UNKNOWN";
        }
    }

    public String getPath() {
        if (path == null) {
            if (soundType == SoundType.INBUILT) {
                path = "/audio/" + name.toLowerCase().replaceAll(" ", "") + ".wav";
            } else if (soundType == SoundType.CUSTOM) {
                path = SaveManager.getAudioDirectory() + name + ".wav";
            }
        }
        return path;
    }

    public URL getURL() {
        if (url == null) {
            if (soundType == SoundType.INBUILT) {
                this.url = getClass().getClassLoader().getResource(getPath());
            } else if (soundType == SoundType.CUSTOM) {
                File file = new File(getPath());
                try {
                    url = file.toURI().toURL();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return url;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sound sound = (Sound) o;
        return Objects.equals(name, sound.name) && soundType == sound.soundType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, soundType);
    }

}
