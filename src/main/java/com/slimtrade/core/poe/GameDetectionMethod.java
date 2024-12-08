package com.slimtrade.core.poe;

/**
 * SlimTrade needs to know where the Path of Exile game window is. This can be done three ways.
 * 1. Periodically check where the game window is (Windows os only)
 * 2. Save the bounds information of a monitor
 * 3. Have the user define a screen region
 */
public enum GameDetectionMethod {

    UNSET, AUTOMATIC, MONITOR, REGION

}
