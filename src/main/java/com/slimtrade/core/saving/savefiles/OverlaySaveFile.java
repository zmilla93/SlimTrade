package com.slimtrade.core.saving.savefiles;

import com.slimtrade.core.References;
import com.slimtrade.core.enums.Anchor;
import com.slimtrade.core.enums.ExpandDirection;

import java.awt.*;

/***
 * Class representation of overlays.json
 */
public class OverlaySaveFile extends BaseSaveFile {

    public Point messageLocation = References.DEFAULT_MESSAGE_LOCATION;
    public ExpandDirection messageExpandDirection = ExpandDirection.DOWNWARDS;
    public Point menubarLocation = References.DEFAULT_MENUBAR_LOCATION;
    public Anchor menubarAnchor = Anchor.TOP_LEFT;
    public int messageWidth = 400;

}
