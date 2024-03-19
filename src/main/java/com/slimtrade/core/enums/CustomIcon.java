package com.slimtrade.core.enums;

import com.slimtrade.gui.buttons.IIcon;

public enum CustomIcon implements IIcon {

    BEAKER("/icons/custom/beakerx48.png"),
    BOOKMARK("/icons/custom/bookmarkx48.png"),
    CANCEL("/icons/custom/cancelx48.png"),
    CART("/icons/custom/cartx64.png"),
    CLOCK("/icons/custom/clockx64.png"),
    FLOW2("/icons/custom/flow-switchx48.png"),
    HOME("/icons/custom/homex64.png"),
    INVITE("/icons/custom/invitex48.png"),
    LEAVE("/icons/custom/leavex64.png"),
    MAIL1("/icons/custom/mailx64.png"),
    MAP("/icons/custom/mapx64.png"),
    REFRESH("/icons/custom/refreshx64.png"),
    REPLY("/icons/custom/replyx48.png"),
    THUMB("/icons/custom/thumbx64.png"),
    WARP("/icons/custom/warpx64.png"),
    WATCH("/icons/custom/watchx64.png"),
    ;

    private final String path;

    CustomIcon(String path) {
        this.path = path;
    }

    @Override
    public String path() {
        return path;
    }

}
