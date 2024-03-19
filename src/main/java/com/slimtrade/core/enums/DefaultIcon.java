package com.slimtrade.core.enums;

import com.slimtrade.gui.buttons.IIcon;

public enum DefaultIcon implements IIcon {

    ARROW_SYNC("/icons/default/arrow-sync.png"),
    ARROW_DOWN("/icons/default/arrow-downx48.png"),
    ARROW_UP("/icons/default/arrow-upx48.png"),
    CALENDER("/icons/default/calender.png"),
    CLOSE("/icons/default/closex64.png"),
    HOME("/icons/custom/homex64.png"),
    LIST("/icons/default/th-listx48.png"),
    PIN1("/icons/default/pin1x48.png"),
    PIN2("/icons/default/pin2x48.png"),
    PLAY("/icons/default/playx64.png"),
    POWER("/icons/default/power.png"),
    STOPWATCH("/icons/default/stopwatch.png"),
    TAG("/icons/default/tagx64.png"),
    TAG_CROPPED("/icons/default/tag-cropped.png"),
    SCANNER("/icons/default/rss.png"),
    ;

    private final String path;

    DefaultIcon(String path) {
        this.path = path;
    }

    @Override
    public String path() {
        return path;
    }

}
