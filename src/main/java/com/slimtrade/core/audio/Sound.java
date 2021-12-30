package com.slimtrade.core.audio;

import com.slimtrade.core.managers.SaveManager;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class Sound {

    public enum SoundType {INBUILT, CUSTOM}

    private final String name;
    private SoundType soundType;
    private transient final String path;
    private transient URL url;

    public Sound(String name, String path, SoundType soundType) {
        if (soundType == SoundType.CUSTOM)
            this.name = "*" + name;
        else
            this.name = name;
        this.path = path;
        this.soundType = soundType;

        if (soundType == SoundType.INBUILT) {
            this.url = getClass().getClassLoader().getResource(path);
        } else {
            File file = new File(SaveManager.getAudioDirectory() + path);
            try {
                url = file.toURI().toURL();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    public String getPath() {
        return path;
    }

    public URL getURL() {
        return url;
    }

    @Override
    public String toString() {
        return name;
    }

}
