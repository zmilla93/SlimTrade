package github.zmilla93.gui.stash;

import github.zmilla93.core.poe.POEWindow;
import github.zmilla93.core.utility.ZUtil;

import java.awt.*;

public class StashHelperContainerPoe1 extends StashHelperContainer {

    @Override
    public void updateLocation() {
        ZUtil.invokeLater(() -> {
            Rectangle stashBounds = POEWindow.getPoe1StashBonds();
            if (stashBounds == null) return;
            pack();
            int adjustedY = stashBounds.y - POEWindow.getPoe1StashHelperOffset() - getHeight();
            setLocation(stashBounds.x, adjustedY);
        });
    }

}
