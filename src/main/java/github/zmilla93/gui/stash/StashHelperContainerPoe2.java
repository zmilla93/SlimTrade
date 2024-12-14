package github.zmilla93.gui.stash;

import github.zmilla93.core.poe.POEWindow;
import github.zmilla93.core.utility.ZUtil;

import java.awt.*;

public class StashHelperContainerPoe2 extends StashHelperContainer {

    @Override
    public void updateLocation() {
        ZUtil.invokeLater(() -> {
            Rectangle stashBounds = POEWindow.getPoe2StashBonds();
            if (stashBounds == null) return;
            pack();
            int adjustedY = stashBounds.y - POEWindow.getPoe2StashHelperOffset() - getHeight();
            setLocation(stashBounds.x, adjustedY);
        });
    }

}
