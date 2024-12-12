package github.zmilla93.core.poe;

import github.zmilla93.core.utility.ZUtil;

/**
 * SlimTrade needs to know where the Path of Exile game window is. This can be done three ways.
 * 1. Periodically check where the game window is (Windows os only)
 * 2. Save the bounds information of a monitor
 * 3. Have the user define a screen region
 */
public enum GameWindowMode {

    UNSET, DETECT, MONITOR, SCREEN_REGION;

    private final String name;

    GameWindowMode() {
        this.name = ZUtil.enumToString(name());
    }

    @Override
    public String toString() {
        return name;
    }

}
