package com.slimtrade.core.ninja;

public class NinjaGridSection {

    public final int cellSize;
    public final int x;
    public final int y;
    public final int spacingX;
    public final int spacingY;
    public final String[][] data;

    public NinjaGridSection(int cellSize, int x, int y, int spacingX, int spacingY, String[][] data) {
        this.cellSize = cellSize;
        this.x = x;
        this.y = y;
        this.spacingX = spacingX;
        this.spacingY = spacingY;
        this.data = data;
    }

}
