package github.zmilla93.core.saving.savefiles;

import github.zmilla93.core.enums.Anchor;
import github.zmilla93.core.enums.ExpandDirection;
import github.zmilla93.core.enums.SliderRange;
import github.zmilla93.core.poe.POEWindow;
import github.zmilla93.core.poe.ScaledInt;

import java.awt.*;

/***
 * Class representation of overlays.json
 */
public class OverlaySaveFile extends AbstractSaveFile {

    public static final Point DEFAULT_MESSAGE_LOCATION = new Point(800, 0);
    public static final Point DEFAULT_MENUBAR_LOCATION = new Point(0, 0);

    public Point messageLocation = DEFAULT_MESSAGE_LOCATION;
    public ExpandDirection messageExpandDirection = ExpandDirection.DOWNWARDS;
    public Point menubarLocation = DEFAULT_MENUBAR_LOCATION;
    public Anchor menubarAnchor = Anchor.TOP_LEFT;
    public int messageWidth = SliderRange.MESSAGE_WIDTH.START;
    public boolean hasInitLocations = false;

    private static final float DEFAULT_MESSAGE_X_PERCENT = ScaledInt.getPercentValue(1400, 1920);

    /**
     * These values need to be initialized after the setup has run,
     * so that they are based on the correct game window locations.
     */
    public boolean initMessageAndMenuBarLocations() {
        if (hasInitLocations) return false;
        applyDefaultMessageLocation();
        applyDefaultMenuBarLocation();
        hasInitLocations = true;
        return true;
    }

    // FIXME : Move these to FrameManager?
    public void applyDefaultMessageLocation() {
        int messageX = POEWindow.getGameBounds().x
                + ScaledInt.getScaledValue(DEFAULT_MESSAGE_X_PERCENT, POEWindow.getGameBounds().width)
                - SliderRange.MESSAGE_WIDTH.START;
        messageLocation = new Point(messageX, 0);
        messageExpandDirection = ExpandDirection.DOWNWARDS;
        messageWidth = SliderRange.MESSAGE_WIDTH.START;
    }

    public void applyDefaultMenuBarLocation() {
        menubarLocation = POEWindow.getGameBounds().getLocation();
        menubarAnchor = Anchor.TOP_LEFT;
    }

    @Override
    public int getCurrentTargetVersion() {
        return 1;
    }

}
