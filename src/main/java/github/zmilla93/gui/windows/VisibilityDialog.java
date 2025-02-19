package github.zmilla93.gui.windows;

import github.zmilla93.gui.managers.VisibilityManager;
import github.zmilla93.modules.theme.components.ThemeDialog;

import javax.swing.*;

/**
 * This is the root of all custom windows.
 * Registers with the theme system, allowing themes to be changed during runtime.
 * Hooks into the visibility manager, which allows windows to retain their visibility when the overlay is shown/hidden.
 */
public class VisibilityDialog extends ThemeDialog {

    private boolean visible;
    private boolean ignoreVisibilitySystem = false;

    public VisibilityDialog() {
        assert SwingUtilities.isEventDispatchThread();
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
        if (visible) {
            setAlwaysOnTop(false);
            setAlwaysOnTop(true);
        }
    }

    @Override
    public void dispose() {
        VisibilityManager.removeFrame(this);
        super.dispose();
    }

}
