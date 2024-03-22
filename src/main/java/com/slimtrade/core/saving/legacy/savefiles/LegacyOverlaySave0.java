package com.slimtrade.core.saving.legacy.savefiles;

import com.slimtrade.core.enums.Anchor;
import com.slimtrade.core.enums.ExpandDirection;
import com.slimtrade.core.saving.savefiles.AbstractSaveFile;

import java.awt.*;

public class LegacyOverlaySave0 extends AbstractSaveFile {

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
    public static Dimension DEFAULT_MESSAGE_SIZE = new Dimension(400, 40);
    public static int TOTAL_BORDER_SIZE = 8;

    @Override
    public int getTargetFileVersion() {
        return 0;
    }

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
