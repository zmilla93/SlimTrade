package com.slimtrade.core.ninja;

import com.slimtrade.core.managers.SaveManager;

import static com.slimtrade.core.ninja.NinjaTab.scaleValue;

/**
 * A group of stash slots that are evenly aligned.
 */
public class NinjaGridSection {

    private final int unscaledCellSize;
    private final int unscaledX;
    private final int unscaledY;
    private final int unscaledSpacingX;
    private final int unscaledSpacingY;
    public int cellSize;
    public int x;
    public int y;
    public int spacingX;
    public int spacingY;
    public final String[][] data;

    public NinjaGridSection(int unscaledCellSize, int unscaledX, int unscaledY, int unscaledSpacingX, int spacingY, String[][] data) {
        this.unscaledCellSize = unscaledCellSize;
        this.unscaledX = unscaledX;
        this.unscaledY = unscaledY;
        this.unscaledSpacingX = unscaledSpacingX;
        this.unscaledSpacingY = spacingY;
        this.data = data;
        applyScaling();
    }

    public void applyScaling() {
        int width = SaveManager.stashSaveFile.data.gridRect.width;
        int height = SaveManager.stashSaveFile.data.gridRect.height;
        this.cellSize = scaleValue(unscaledCellSize, height);
        this.x = scaleValue(unscaledX, width);
        this.y = scaleValue(unscaledY, height);
        this.spacingX = scaleValue(unscaledSpacingX, width);
        this.spacingY = scaleValue(unscaledSpacingY, height);
    }

}
