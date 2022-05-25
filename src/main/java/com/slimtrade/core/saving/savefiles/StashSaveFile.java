package com.slimtrade.core.saving.savefiles;

import java.awt.*;

public class StashSaveFile {

    public Rectangle windowRect;
    public Rectangle gridRect;
    private transient Dimension cellSize;

    public void buildCache() {
        int width = (int) Math.ceil(windowRect.width / 12f);
        int height = (int) Math.ceil(windowRect.height / 12f);
        cellSize = new Dimension(width, height);
    }

    public Dimension getCellSize() {
        return cellSize;
    }

}
