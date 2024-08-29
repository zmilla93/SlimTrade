package com.slimtrade.gui.ninja;

import com.slimtrade.core.managers.SaveManager;

import java.awt.*;

import static com.slimtrade.core.ninja.NinjaTab.scaleValue;

public class NinjaVirtualTabButton {

    public final String name;
    public final Rectangle unscaledRect;
    public Rectangle rect;

    public NinjaVirtualTabButton(String name, Rectangle unscaledRect) {
        this.name = name;
        this.unscaledRect = unscaledRect;
        applyScaling();
    }

    public void applyScaling() {
        int width = SaveManager.stashSaveFile.data.gridRect.width;
        int height = SaveManager.stashSaveFile.data.gridRect.height;
        rect = new Rectangle(scaleValue(unscaledRect.x, width), scaleValue(unscaledRect.y, height),
                scaleValue(unscaledRect.width, width), scaleValue(unscaledRect.height, height));
    }

}
