package github.zmilla93.core.enums;

import github.zmilla93.gui.buttons.IIcon;

public enum DefaultIcon implements IIcon {

    APP_ICON("/icons/default/chaos-orb.png"),
    ARROW_SYNC("/icons/default/arrow-sync.png"),
    ARROW_DOWN("/icons/default/arrow-down.png"),
    ARROW_UP("/icons/default/arrow-up.png"),
    CALENDER("/icons/default/calender.png"),
    CHAOS_ORB("/icons/default/chaos-orb.png"),
    CHART("/icons/default/chart-area.png"),
    CANCEL("/icons/default/cancel.png"),
    CLOSE("/icons/default/close.png"),
    COG("/icons/default/cog.png"),
    DRAG("/icons/default/drag1.png"),
    EYE("/icons/default/eye.png"),
    EYE_OUTLINE("/icons/default/eye-outline.png"),
    HIDDEN("/icons/default/hidden.png"),
    HISTORY("/icons/default/history.png"),
    HOME("/icons/default/home.png"),
    LIST("/icons/default/th-list.png"),
    PIN1("/icons/default/pin1.png"),
    PIN2("/icons/default/pin2.png"),
    PLAY("/icons/default/play.png"),
    PLUS("/icons/default/plus.png"),
    POWER("/icons/default/power.png"),
    STOPWATCH("stopwatch.png"),
    REFRESH("/icons/default/refresh.png"),
    TAG("/icons/default/tag.png"),
    TAG_CROPPED("/icons/default/tag-cropped.png"),
    VOLUME("/icons/default/volume.png"),
    VOLUME_DOWN("/icons/default/volume-down.png"),
    VOLUME_MUTE("/icons/default/volume-mute.png"),
    VOLUME_UP("/icons/default/volume-up.png"),
    SCANNER_ON("/icons/default/message-typing.png"),
    SCANNER_OFF("/icons/default/message.png"),
    ;

    private final String path;

    DefaultIcon(String fileName) {
        this.path = "" + fileName + "";
    }

    @Override
    public String path() {
        return path;
    }

}
