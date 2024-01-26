package com.slimtrade.core.saving.savefiles;

import com.slimtrade.core.References;
import com.slimtrade.core.enums.Anchor;
import com.slimtrade.core.enums.ExpandDirection;
import com.slimtrade.core.enums.SliderRange;

import java.awt.*;

/***
 * Class representation of overlays.json
 */
public class OverlaySaveFile extends AbstractSaveFile {

    public Point messageLocation = References.DEFAULT_MESSAGE_LOCATION;
    public ExpandDirection messageExpandDirection = ExpandDirection.DOWNWARDS;
    public Point menubarLocation = References.DEFAULT_MENUBAR_LOCATION;
    public Anchor menubarAnchor = Anchor.TOP_LEFT;
    public int messageWidth = SliderRange.MESSAGE_WIDTH.START;

    @Override
    public int getTargetFileVersion() {
        return 1;
    }

}
