package github.zmilla93.gui.stash;

import github.zmilla93.core.poe.POEWindow;

import javax.swing.*;
import java.awt.*;

public class StashHelperContainerPoe1 extends StashHelperContainer {

    @Override
    public void updateLocation() {
        SwingUtilities.invokeLater(() -> {
            Rectangle stashBounds = POEWindow.getPoe1StashBonds();
            if (stashBounds == null) return;
            setLocation(stashBounds.x, stashBounds.y);
            pack();
        });
    }

    @Override
    public void onGameBoundsChange() {
        updateLocation();
    }

}
