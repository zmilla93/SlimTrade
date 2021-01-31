package com.slimtrade.core.audio;

import java.net.URL;

public enum Sound {

    PING1("Ping 1", "audio/ping1.wav"),
    PING2("Ping 2", "audio/ping2.wav"),
    BLIP1("Blip 1", "audio/blip1.wav"),
    BLIP2("Blip 2", "audio/blip2.wav"),
    BLIP3("Blip 3", "audio/blip3.wav"),
    ;

    private final String name;
    private final String path;
    private final URL url;

    Sound(String name, String path) {
        this.name = name;
        this.path = path;
        url = this.getClass().getClassLoader().getResource(path);
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
