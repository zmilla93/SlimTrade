package com.slimtrade.gui.windows;

import com.slimtrade.gui.managers.VisibilityManager;
import com.slimtrade.modules.theme.ThemeManager;

import javax.swing.*;

/**
 * This is the root of all custom windows.
 * Registers with the theme system, allowing themes to be changed during runtime.
 * Hooks into the visibility manager, which allows windows to retain their visibility when the overlay is shown/hidden.
 */
public class VisibilityDialog extends JDialog {

    private boolean visible;
    private boolean ignoreVisibilitySystem = false;

    public VisibilityDialog() {
        VisibilityManager.addFrame(this);
        ThemeManager.addFrame(this);
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

    @Override
    public void dispose() {
        VisibilityManager.removeFrame(this);
        ThemeManager.removeFrame(this);
        super.dispose();
    }

}
