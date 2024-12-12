package github.zmilla93.core.poe;

import github.zmilla93.core.utility.ZUtil;

/**
 * SlimTrade needs to know where the Path of Exile game window is. This can be done three ways.
 * 1. Save the bounds of the POE game window.
 * 2. Save the bounds of a monitor
 * 3. Have the user define a screen region (not implemented)
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
