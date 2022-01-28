package com.slimtrade.core.data;

public class HotkeyActionMeh {

    public HotkeyType hotkeyType;
    public String data;
    public enum HotkeyType{APP, POE, LINK}

    public HotkeyActionMeh(HotkeyType hotkeyType, String data){
        this.hotkeyType = hotkeyType;
        this.data = data;
    }

}
