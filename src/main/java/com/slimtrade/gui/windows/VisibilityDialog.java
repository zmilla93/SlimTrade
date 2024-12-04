package com.slimtrade.gui.windows;

import com.slimtrade.gui.managers.VisibilityManager;

import javax.swing.*;

/**
 * This is the root of all custom windows, which controls showing and hiding of overlay windows.
 * Windows can also op out of this using ignoreVisibilitySystem().
 */
public class VisibilityDialog extends JDialog {

    private boolean visible;
    private boolean ignoreVisibilitySystem = false;

    public VisibilityDialog() {
        VisibilityManager.addFrame(this);
    }

    public void showOverlay() {
        if (ignoreVisibilitySystem) return;
        setVisible(visible);
    }

    public void hideOverlay() {
        if (ignoreVisibilitySystem) return;
        boolean vis = isVisible();
        setVisible(false);
        visible = vis;
    }

    public void ignoreVisibilitySystem(boolean state) {
        this.ignoreVisibilitySystem = state;
    }

    @Override
    public void setVisible(boolean visible) {
        assert (SwingUtilities.isEventDispatchThread());
        super.setVisible(visible);
        this.visible = visible;
    }

}
