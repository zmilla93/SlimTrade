package github.zmilla93.core.ninja;

import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.modules.saving.ISaveListener;

import java.awt.*;
import java.util.ArrayList;

import static github.zmilla93.core.ninja.NinjaTab.scaleValue;

/**
 * A group of stash slots that are evenly aligned.
 */
public class NinjaGridSection implements ISaveListener {

    private final int unscaledCellSize;
    private final int unscaledX;
    private final int unscaledY;
    private final int unscaledSpacingX;
    private final int unscaledSpacingY;
    // FIXME : Cell size needs to be split by x & y
    public int cellSize;
    public int x;
    public int y;
    public int spacingX;
    public int spacingY;
    public final String[][] data;

    // Spatial data
    public Rectangle boundingBox;
    public ArrayList<Rectangle> buttonRects = new ArrayList<>();

    public NinjaGridSection(int unscaledCellSize, int unscaledX, int unscaledY, int unscaledSpacingX, int spacingY, String[][] data) {
        this.unscaledCellSize = unscaledCellSize;
        this.unscaledX = unscaledX;
        this.unscaledY = unscaledY;
        this.unscaledSpacingX = unscaledSpacingX;
        this.unscaledSpacingY = spacingY;
        this.data = data;
        applyScaling();
        SaveManager.stashSaveFile.addListener(this);
    }

    public void applyScaling() {
        int width = SaveManager.stashSaveFile.data.gridRect.width;
        int height = SaveManager.stashSaveFile.data.gridRect.height;
        this.cellSize = scaleValue(unscaledCellSize, height);
        this.x = scaleValue(unscaledX, width);
        this.y = scaleValue(unscaledY, height);
        this.spacingX = scaleValue(unscaledSpacingX, width);
        this.spacingY = scaleValue(unscaledSpacingY, height);
        calculateBoundingBox();
        buttonRects.clear();
        for (int indexX = 0; indexX < data.length; indexX++) {
            for (int indexY = 0; indexY < data[indexX].length; indexY++) {
                String value = data[indexX][indexY];
                if (value == null) continue;
                int offsetX = indexY * (cellSize + spacingX);
                int offsetY = indexX * (cellSize + spacingY);
                buttonRects.add(new Rectangle(this.x + offsetX, this.y + offsetY, cellSize, cellSize));
            }
        }
    }

    private void calculateBoundingBox() {
        int cellCountX = 0;
        int cellCountY = data.length;
        for (int i = 0; i < data.length; i++) if (data[i].length > cellCountX) cellCountX = data[i].length;
        int totalSpacingX = cellCountX > 1 ? spacingX * (cellCountX - 1) : 0;
        int totalSpacingY = cellCountY > 1 ? spacingY * (cellCountY - 1) : 0;
        int width = cellCountX * cellSize + totalSpacingX;
        int height = cellCountY * cellSize + totalSpacingY;
        boundingBox = new Rectangle(x, y, width, height);
    }

    @Override
    public void onSave() {
        applyScaling();
    }

}
