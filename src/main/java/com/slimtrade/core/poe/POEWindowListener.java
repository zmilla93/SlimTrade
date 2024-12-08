package com.slimtrade.core.poe;

/**
 * Listens for when the Path of Exile game window changes size or location.
 * Attach to {@link POEWindow}.
 */
public interface POEWindowListener {

    void onGameBoundsChange();

}
