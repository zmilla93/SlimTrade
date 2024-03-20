package com.slimtrade.core.enums;

import com.slimtrade.gui.buttons.IIcon;

public enum CustomIcon implements IIcon {

    BEAKER("beaker"),
    BOOKMARK("bookmark"),
    CANCEL("cancel"),
    CART("cart"),
    CLOCK("clock"),
    FLOW2("flow-switch"),
    HOME("home"),
    INVITE("invite"),
    KICK("user-delete"),
    LEAVE("leave"),
    MAIL1("mail"),
    MAP("map"),
    REFRESH("refresh"),
    REPLY("reply"),
    THUMB("thumb"),
    THUMB_DOWN("thumb-down"),
    WARP("warp"),
    WATCH("watch"),
    ;

    private final String path;

    CustomIcon(String fileName) {
        this.path = "/icons/custom/" + fileName + ".png";
    }

    @Override
    public String path() {
        return path;
    }

}
