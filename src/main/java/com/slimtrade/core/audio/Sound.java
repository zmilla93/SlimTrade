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
    private String displayName;
    public String fileName;
    public SoundType soundType;

    private transient Path path;
    private transient URL url;

    /**
     * Acts as a reference to a .wav file.
     *
     * @param name      Display name for Inbuilt, File name for Custom
     * @param soundType Inbuilt or Custom
     */
    public Sound(String name, SoundType soundType) {
        this.name = name;
        this.soundType = soundType;
    }

    public String getDisplayName() {
        if (displayName == null) displayName = name.replaceAll(".wav", "");
        return displayName;
    }

    public Path getPath() {
        if (path == null) {
            if (soundType == SoundType.INBUILT) {
                try {
                    path = Paths.get(getURL().toURI());
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            } else if (soundType == SoundType.CUSTOM) {
                path = SaveManager.getAudioDirectory().resolve(getFileName());
            }
        }
        return path;
    }

    public URL getURL() {
        if (url == null) {
            if (soundType == SoundType.INBUILT) {
                this.url = Objects.requireNonNull(getClass().getResource(getFileName()));
            } else if (soundType == SoundType.CUSTOM) {
                try {
                    this.url = getPath().toUri().toURL();
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return url;
    }

    private String getFileName() {
        if (fileName == null) {
            if (soundType == SoundType.INBUILT) {
                String audioFolderName = SaveManager.getAudioDirectory().getFileName().toString();
                fileName = "/" + audioFolderName + "/" + name.toLowerCase().replaceAll(" ", "") + ".wav";
            } else fileName = name;
        }
        return fileName;
    }

    /// A more debug friendly version of toString().
    public String getDetails() {
        return "Sound[" + soundType + ", " + getFileName() + "]";
    }

    @Override
    public String toString() {
        return getDisplayName();
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
