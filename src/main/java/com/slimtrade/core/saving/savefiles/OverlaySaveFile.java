package com.slimtrade.core.saving.savefiles;

import com.slimtrade.core.References;
import com.slimtrade.core.enums.ExpandDirection;

import java.awt.*;

/***
 * Class representation of overlays.json
 */
public class OverlaySaveFile {

    public Point messageLocation = References.DEFAULT_MESSAGE_LOCATION;
    public ExpandDirection expandDirection = ExpandDirection.DOWNWARDS;
    public int messageWidth = 400;

}
