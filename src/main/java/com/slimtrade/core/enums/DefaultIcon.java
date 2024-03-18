package com.slimtrade.core.enums;

import com.slimtrade.gui.buttons.IIcon;
import com.slimtrade.modules.saving.ISavable;

public enum DefaultIcon implements IIcon, ISavable {

    SLIM_ICON("/icons/slim-icon-2.png"),
    CHAOS_ICON("/icons/chaos-icon.png"),

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
    SCANNER("/icons/default/rss.png"),
    ;

    public final String path;

    DefaultIcon(String path) {
        this.path = path;
    }

    @Override
    public void save() {

    }

    @Override
    public void load() {

    }

    @Override
    public String path() {
        return path;
    }

}
