package com.slimtrade.core.data;

public class Hotkey {

    public String name;
    public HotkeyType hotkeyType;
    public KeystrokeData keystroke;

    public enum HotkeyType { APP, POE, LINK}

    public Hotkey(String name, HotkeyType hotkeyType, KeystrokeData keystroke){
        this.name = name;
        this.hotkeyType = hotkeyType;
        this.keystroke = keystroke;
    }

}
