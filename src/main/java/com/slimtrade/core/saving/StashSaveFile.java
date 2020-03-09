package com.slimtrade.core.saving;

import java.awt.*;

public class StashSaveFile {

    public boolean initialized = false;
    public int windowX, windowY, windowWidth, windowHeight, gridX, gridY, gridWidth, gridHeight;

    public StashSaveFile() {

        // Tries to initialize the stash according to the monitor size
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        windowX = (int) Math.round(screen.width * 0.0026);
        windowY = (int) Math.round(screen.height * 0.0925);
        windowWidth = (int) Math.round(screen.width * 0.34375);
        windowHeight = (int) Math.round(screen.height * 0.72963);

    }

}
