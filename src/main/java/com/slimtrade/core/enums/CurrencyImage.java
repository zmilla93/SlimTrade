package com.slimtrade.core.enums;

import com.slimtrade.core.managers.SaveManager;

import javax.swing.*;
import java.util.Objects;

/**
 * Stores metadata for POE currency icons.
 */
public enum CurrencyImage {

    ;

    public final String fullName;    // Timeless Delirium Orb
    private String partialName; // Timeless
    private String fileName;    // Timeless_Delirium_Orb.png

    // Internal
    private int cachedImageSize;
    private ImageIcon imageIcon;

    CurrencyImage(String fullName) {
        this.fullName = fullName;
    }

    public String getPartialName() {
        if (partialName == null) {
            // TODO
        }
        return partialName;
    }

    private String getFileName() {
        if (fileName == null) {
            fileName = fullName.replaceAll(" ", "_").replaceAll(":", "");
        }
        return fileName;
    }

    public ImageIcon getIcon() {
        if (imageIcon == null || cachedImageSize != SaveManager.settingsSaveFile.data.iconSize) {
            cachedImageSize = SaveManager.settingsSaveFile.data.iconSize;
            imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/currency/" + getFileName())));
        }
        return imageIcon;
    }

}
