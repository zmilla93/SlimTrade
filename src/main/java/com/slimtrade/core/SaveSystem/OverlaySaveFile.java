package com.slimtrade.core.SaveSystem;

import com.slimtrade.enums.MenubarButtonLocation;
import com.slimtrade.gui.enums.ExpandDirection;

public class OverlaySaveFile {

    public boolean messageScreenLock, menubarScreenLock;
    public int messageX = 400, messageY = 0, menubarX = 0, menubarY = 0, menubarWidth, menubarHeight;
    public ExpandDirection messageExpandDirection = ExpandDirection.DOWN;
    public MenubarButtonLocation menubarButtonLocation = MenubarButtonLocation.NW;


}
