package github.zmilla93.core.saving.savefiles;

import github.zmilla93.core.References;
import github.zmilla93.core.enums.Anchor;
import github.zmilla93.core.enums.ExpandDirection;
import github.zmilla93.core.enums.SliderRange;

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
    public int getCurrentTargetVersion() {
        return 1;
    }

}
