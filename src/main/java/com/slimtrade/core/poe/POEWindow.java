package com.slimtrade.core.poe;

import com.slimtrade.gui.windows.BasicDialog;

import java.awt.*;
import java.util.ArrayList;

/**
 * A platform independent representation of the Path of Exile game window.
 * Used to calculate the screen location of in game UI elements, or to pin things relative to the game window.
 * Can be updated using the bounds of a monitor, or by listening to platform specific window events.
 * Attach a {@link POEWindowListener} to listen for events.
 */
// TODO : Could also add the option to manually define the game window region to support Mac/Linux users who play in windowed mode.
public class POEWindow {
    // FIXME : Temp static info
    private static final Rectangle POE_1_STASH_1920_1080 = new Rectangle(13, 126, 634, 634);

    private static Rectangle gameBounds = new Rectangle(0, 0, 1920, 1080);
    private static Rectangle poe1StashBounds = POE_1_STASH_1920_1080;
    private static Dimension poe1StashCellSize;
    private static Dimension poe1StashCellSizeQuad;

    private static final ArrayList<POEWindowListener> listeners = new ArrayList<>();

    // FIXME : Temp dialog
    public static BasicDialog dialog;

    static {
        // FIXME : Calculating bounds early using 1920x1080 to avoid errors.
        //  Should instead use the bounds of the first monitor, then let the automated system take over.
        calculatePoe1StashBounds();
    }

    // FIXME : Temp function
    public static void createAndShowWindow() {
        dialog = new BasicDialog();
        dialog.setBounds(POE_1_STASH_1920_1080);
        dialog.setVisible(true);
    }

    public static Rectangle getGameBounds() {
        return gameBounds;
    }

    public static void setGameBounds(Rectangle gameBounds) {
        if (POEWindow.gameBounds.equals(gameBounds)) return;
        POEWindow.gameBounds = gameBounds;
        calculatePoe1StashBounds();
        for (POEWindowListener listener : listeners) listener.onGameBoundsChange();
    }

    public static Rectangle getPoe1StashBonds() {
        return poe1StashBounds;
    }

    public static Dimension getPoe1StashCellSize() {
        return poe1StashCellSize;
    }

    public static Dimension getPoe1StashCellSizeQuad() {
        return poe1StashCellSizeQuad;
    }

    private static void calculatePoe1StashBounds() {
        // FIXME : Scale stash bounds
        POEWindow.poe1StashBounds = new Rectangle(
                gameBounds.x + POE_1_STASH_1920_1080.x, gameBounds.y + POE_1_STASH_1920_1080.y,
                POE_1_STASH_1920_1080.width, POE_1_STASH_1920_1080.height);
        int cellWidth = Math.round(poe1StashBounds.width / 12f);
        int cellHeight = Math.round(poe1StashBounds.height / 12f);
        poe1StashCellSize = new Dimension(cellWidth, cellHeight);
        int quadCellWidth = Math.round(poe1StashBounds.width / 24f);
        int quadCellHeight = Math.round(poe1StashBounds.height / 24f);
        poe1StashCellSizeQuad = new Dimension(quadCellWidth, quadCellHeight);
    }

    public static void addListener(POEWindowListener listener) {
        listeners.add(listener);
    }

    public static void removeListener(POEWindowListener listener) {
        listeners.remove(listener);
    }

}
