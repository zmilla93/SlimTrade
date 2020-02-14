package com.slimtrade.gui.enums;

import com.slimtrade.core.References;

import java.awt.Image;
import java.util.Objects;

import javax.swing.ImageIcon;

/**
 * Preloaded icons used by the SlimTrade UI. These are intended for internal use only.
 * PreloadedImageCustom is for use by the user with custom macro buttons.
 */

public enum PreloadedImage {
    CART("icons/default/cartx64.png"),
    CLOSE("icons/default/closex64.png"),
    HOME("icons/default/homex64.png"),
    INVITE("icons/default/invitex48.png"),
    LEAVE("icons/default/leavex64.png"),
    PLAY("icons/default/playx64.png"),
    REFRESH("icons/default/refreshx64.png"),
    REPLY("icons/custom/replyx48.png"),
    TAG("icons/default/tagx64.png"),
    THUMB("icons/default/thumbx64.png"),
    WARP("icons/default/warpx64.png"),
    ;

    private Image image;
    private final String path;
    private int cachedSize = 0;

    PreloadedImage(String path) {
        this.path = path;
    }

    public Image getImage() {
        return getImage(References.DEFAULT_IMAGE_SIZE);
    }

    public Image getImage(int size) {
        if(image == null || size != cachedSize) {
            image = new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource(path))).getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
            cachedSize = size;
        }
        return image;
    }

}
