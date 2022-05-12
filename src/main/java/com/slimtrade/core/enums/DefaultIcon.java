package com.slimtrade.core.enums;

public enum DefaultIcon {

    ARROW_DOWN("/icons/default/arrow-downx48.png"),
    ARROW_UP("/icons/default/arrow-upx48.png"),
    CLOSE("/icons/default/closex64.png"),
    PIN1("/icons/default/pin1x48.png"),
    PIN2("/icons/default/pin2x48.png"),
    PLAY("/icons/default/playx64.png"),
    TAG("/icons/default/tagx64.png"),
    SLIM_ICON("/icons/slim-icon-2.png"),
    ;

    public String path;

    DefaultIcon(String path) {
        this.path = path;
    }

}
