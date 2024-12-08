package com.slimtrade.core.audio;

import com.slimtrade.core.managers.SaveManager;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Represents a .wav audio file somewhere on disk,
 * either in the JAR file (INBUILT) or in the SlimTrade audio directory (CUSTOM).
 */
public class Sound {

    public enum SoundType {INBUILT, CUSTOM}

    public final String name;
    public SoundType soundType;

    private transient String pathString;
    private transient Path path;
    private transient URL url;

    public Sound(String name, SoundType soundType) {
        if (name == null) name = "null";
        this.soundType = soundType;
        if (soundType == SoundType.INBUILT) {
            this.name = name;
            pathString = "/audio/" + name.toLowerCase().replaceAll(" ", "") + ".wav";
        } else if (soundType == SoundType.CUSTOM) {
            this.name = name.replaceFirst("\\.wav\\Z", "");
            pathString = name;
        } else {
            this.name = "UNDEFINED_SOUND_TYPE";
        }
    }

    public Path getPath() {
        if (path != null) return path;
        if (soundType == SoundType.INBUILT) {
            try {
                path = Paths.get(getURL().toURI());
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        } else if (soundType == SoundType.CUSTOM) {
            path = SaveManager.getAudioDirectory().resolve(pathString);
        }
        return path;
    }

    public URL getURL() {
        if (url != null) return url;
        if (soundType == SoundType.INBUILT) {
            this.url = Objects.requireNonNull(getClass().getResource(pathString));
        } else if (soundType == SoundType.CUSTOM) {
            try {
                this.url = getPath().toUri().toURL();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
        return url;
    }

    /// A more debug friendly version of toString().
    public String getDetails() {
        return "Sound[" + soundType + ", " + pathString + "]";
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
