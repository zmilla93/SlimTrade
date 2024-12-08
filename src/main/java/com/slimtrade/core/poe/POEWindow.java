package com.slimtrade.core.poe;

import com.slimtrade.gui.windows.BasicDialog;

import java.awt.*;

/**
 * A platform independent representation of the Path of Exile game window.
 * Can be updated using the bounds of a monitor, or by listening to platform specific window events.
 */
public class POEWindow {

    // FIXME : Is title actually needed? It isn't very useful and it won't be set correctly when using monitor bounds anyway.
//    private static String title;
    private static Rectangle gameBounds;
    private static Rectangle poe1StashBounds;
    private static Dimension poe1StashCellSize;
    private static Dimension poe1StashCellSizeQuad;

    // FIXME : Temp static info
    private static final Rectangle POE_1_STASH_1920_1080 = new Rectangle(13, 126, 634, 634);
    // FIXME : Temp dialog
    public static BasicDialog dialog;

    // FIXME : Temp function
    public static void createAndShowWindow() {
        dialog = new BasicDialog();
        dialog.setBounds(POE_1_STASH_1920_1080);
        dialog.setVisible(true);
    }

//    public static void setWindow(String title, Rectangle gameBounds) {
////        setTitle(title);
//        setGameBounds(gameBounds);
//    }

//    public static String getTitle() {
//        return title;
//    }
//
//    public static void setTitle(String title) {
//        POEWindow.title = title;
//    }

    public static Rectangle getGameBounds() {
        return gameBounds;
    }

    public static void setGameBounds(Rectangle gameBounds) {
        POEWindow.gameBounds = gameBounds;
        calculatePoe1StashBounds();
    }

    public Rectangle getPoe1StashBonds() {
        return poe1StashBounds;
    }

    public Dimension getPoe1StashCellSize() {
        return poe1StashCellSize;
    }
    public Dimension getPoe1StashCellSizeQuad() {
        return poe1StashCellSizeQuad;
    }

    private static void calculatePoe1StashBounds() {
        // FIXME : Scale stash bounds
        POEWindow.poe1StashBounds = new Rectangle(
                gameBounds.x + POE_1_STASH_1920_1080.x, gameBounds.y + POE_1_STASH_1920_1080.y,
                POE_1_STASH_1920_1080.width, POE_1_STASH_1920_1080.height);
        int width = Math.round(gameBounds.width / 12f);
        int height = Math.round(gameBounds.height / 12f);
        int widthQuad = Math.round(gameBounds.width / 24f);
        int heightQuad = Math.round(gameBounds.height / 24f);
        poe1StashCellSize = new Dimension(width, height);
        poe1StashCellSizeQuad = new Dimension(widthQuad, heightQuad);
    }

}
