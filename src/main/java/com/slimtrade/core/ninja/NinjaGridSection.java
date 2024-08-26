package com.slimtrade.core.ninja;

public class NinjaGridSection {

    public final int x;
    public final int y;
    public final int spacingX;
    public final int spacingY;
    public final String[][] data;

    public NinjaGridSection(int x, int y, int spacingX, int spacingY, String[][] data) {
        this.x = x;
        this.y = y;
        this.spacingX = spacingX;
        this.spacingY = spacingY;
        this.data = data;
    }

}
