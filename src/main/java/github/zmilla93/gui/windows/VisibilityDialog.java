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
    private boolean debounceWindowFocus = false;
    private final Timer timer = new Timer(200, e -> debounceWindowFocus = false);

    public VisibilityDialog() {
        assert SwingUtilities.isEventDispatchThread();
        timer.setRepeats(false);
        VisibilityManager.addFrame(this);
        setAlwaysOnTop(true);
        // FIXME : This fixes window layering issues, but can cause an infinite loop without debouncing.
        //  A better solution would be nice before a full release
        //  Idea: Track gain/lose events, see what order of execution is
//        addWindowFocusListener(new WindowFocusListener() {
//            @Override
//            public void windowGainedFocus(WindowEvent e) {
//                if (debounceWindowFocus) return;
////                setAlwaysOnTop(false);
////                setAlwaysOnTop(true);
//                debounceWindowFocus = true;
//                timer.restart();
//            }
//
//            @Override
//            public void windowLostFocus(WindowEvent e) {
//
//            }
//        });
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

    /**
     * Forces the window to the front in a way that accounts for isAlwaysOnTop
     */
    @Deprecated // FIXME This causes weird visibility issues.
//    public void forceToFront() {
//        boolean wasAlwaysOnTop = isAlwaysOnTop();
//        setAlwaysOnTop(false);
//        toFront();
////        requestFocus();
//        setAlwaysOnTop(true);
//    }

    @Override
    public void setVisible(boolean visible) {
        assert (SwingUtilities.isEventDispatchThread());
//        if (visible) forceToFront();
        super.setVisible(visible);
        this.visible = visible;

    }

    @Override
    public void dispose() {
        VisibilityManager.removeFrame(this);
        super.dispose();
    }

}
