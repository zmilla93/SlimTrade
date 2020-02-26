package com.slimtrade.core.saving;

import com.slimtrade.enums.MenubarButtonLocation;
import com.slimtrade.gui.enums.ExpandDirection;

public class OverlaySaveFile {

    // Message Manager Info
    public boolean messageScreenLock = true;
    public int messageX = 400, messageY = 0;
    public ExpandDirection messageExpandDirection = ExpandDirection.DOWN;

    // MenuBar Info
    public boolean menubarScreenLock = true;
    public int menubarX = 0, menubarY = 0;
    public int menubarWidth, menubarHeight;
    public MenubarButtonLocation menubarButtonLocation = MenubarButtonLocation.NW;

}
