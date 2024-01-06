package com.slimtrade.core.saving.legacy.savefiles;

import com.slimtrade.core.enums.Anchor;
import com.slimtrade.core.enums.ExpandDirection;
import com.slimtrade.core.saving.savefiles.BaseSaveFile;

public class LegacyOverlaySave0 extends BaseSaveFile {

    // Message Manager Info
    public boolean messageScreenLock = true;
    public int messageX = 400, messageY = 0;
    public LegacyExpandDirection messageExpandDirection = LegacyExpandDirection.DOWN;
    public int messageSizeIncrease = 0;

    // MenuBar Info
    public boolean menubarScreenLock = true;
    public int menubarX = 0, menubarY = 0;
    public int menubarWidth, menubarHeight;
    public LegacyMenubarButtonLocation menubarButtonLocation = LegacyMenubarButtonLocation.NW;

    public enum LegacyExpandDirection {
        UP(ExpandDirection.UPWARDS),
        DOWN(ExpandDirection.DOWNWARDS);

        public final ExpandDirection expandDirection;

        LegacyExpandDirection(ExpandDirection expandDirection) {
            this.expandDirection = expandDirection;
        }
    }

    public enum LegacyMenubarButtonLocation {
        NW(Anchor.TOP_LEFT),
        NE(Anchor.TOP_RIGHT),
        SW(Anchor.BOTTOM_LEFT),
        SE(Anchor.BOTTOM_RIGHT);

        public final Anchor anchor;

        LegacyMenubarButtonLocation(Anchor anchor) {
            this.anchor = anchor;
        }
    }

}
