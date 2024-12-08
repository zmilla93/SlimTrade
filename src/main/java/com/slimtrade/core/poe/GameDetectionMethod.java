package com.slimtrade.core.poe;

/**
 * SlimTrade needs to know where the Path of Exile game window is. This can be done one of two ways.
 * 1. Save information about a monitor (all platforms).
 * 2. Periodically check where the game window is (Windows os only)
 * Windowed mode on a non Windows os isn't currently supported, but could be added a similar way to the old stash method if needed
 */
public enum GameDetectionMethod {

    UNSET, MONITOR, GAME

}
