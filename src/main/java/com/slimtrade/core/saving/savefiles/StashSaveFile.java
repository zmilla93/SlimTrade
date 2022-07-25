package com.slimtrade.core.saving.savefiles;

import java.awt.*;

public class StashSaveFile {

    public Rectangle windowRect = null;
    public Rectangle gridRect = null;
    private transient Dimension cellSize;
    private transient Dimension cellSizeQuad;
//    public Rectangle pathOfExileBounds;
//    private final float stashOffsetX = 16 / 1920f;
//    private final float stashOffsetY = 126 / 1080f;
//    private final float stashOffsetYFolder = 134 / 1080f;

    public void buildCache() {
        if (gridRect == null) return;
        int width = Math.round(gridRect.width / 12f);
        int height = Math.round(gridRect.height / 12f);
        int widthQuad = Math.round(gridRect.width / 24f);
        int heightQuad = Math.round(gridRect.height / 24f);
        cellSize = new Dimension(width, height);
        cellSizeQuad = new Dimension(widthQuad, heightQuad);
    }

    public Dimension getCellSize() {
        return cellSize;
    }

    public Dimension getCellSizeQuad() {
        return cellSizeQuad;
    }

}
