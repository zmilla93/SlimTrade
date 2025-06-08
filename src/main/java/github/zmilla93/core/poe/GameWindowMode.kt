package github.zmilla93.core.poe

import github.zmilla93.core.utility.ZUtil

/**
 * SlimTrade needs to know where the Path of Exile game window is. This can be done three ways.
 * 1. Save the bounds of the POE game window.
 * 2. Save the bounds of a monitor
 * 3. Have the user define a screen region (not implemented)
 */
enum class GameWindowMode {

    UNSET, DETECT, MONITOR, SCREEN_REGION;

    private val displayName: String = ZUtil.enumToString(name)

    override fun toString(): String {
        return displayName
    }

}
