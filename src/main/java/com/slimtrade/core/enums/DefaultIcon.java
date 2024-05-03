package com.slimtrade.core.enums;

import com.slimtrade.gui.buttons.IIcon;

public enum DefaultIcon implements IIcon {

    ARROW_SYNC("arrow-sync"),
    ARROW_DOWN("arrow-down"),
    ARROW_UP("arrow-up"),
    CALENDER("calender"),
    CHAOS_ORB("chaos-orb"),
    CHART("chart-area"),
    CLOSE("close"),
    COG("cog"),
    HISTORY("history"),
    HOME("home"),
    LIST("th-list"),
    PIN1("pin1"),
    PIN2("pin2"),
    PLAY("play"),
    POWER("power"),
    STOPWATCH("stopwatch"),
    REFRESH("refresh"),
    TAG("tag"),
    TAG_CROPPED("tag-cropped"),
    VOLUME("volume"),
    VOLUME_DOWN("volume-down"),
    VOLUME_MUTE("volume-mute"),
    VOLUME_UP("volume-up"),
    SCANNER_ON("message-typing"),
    SCANNER_OFF("message"),
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
