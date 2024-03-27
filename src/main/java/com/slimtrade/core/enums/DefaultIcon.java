package com.slimtrade.core.enums;

import com.slimtrade.gui.buttons.IIcon;

public enum DefaultIcon implements IIcon {

    ARROW_SYNC("arrow-sync"),
    ARROW_DOWN("arrow-down"),
    ARROW_UP("arrow-up"),
    CALENDER("calender"),
    CHAOS_ORB("chaos-orb"),
    CLOSE("close"),
    HOME("home"),
    LIST("th-list"),
    PIN1("pin1"),
    PIN2("pin2"),
    PLAY("play"),
    POWER("power"),
    STOPWATCH("stopwatch"),
    TAG("tag"),
    TAG_CROPPED("tag-cropped"),
    SCANNER("rss"),
    ;

    private final String path;

    DefaultIcon(String fileName) {
        this.path = "/icons/default/" + fileName + ".png";
    }

    @Override
    public String path() {
        return path;
    }

}
