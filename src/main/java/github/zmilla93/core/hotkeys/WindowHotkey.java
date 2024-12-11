package github.zmilla93.core.hotkeys;

import github.zmilla93.gui.windows.VisibilityDialog;

import javax.swing.*;

/**
 * Toggles the visibility of a swing window.
 */
public class WindowHotkey implements IHotkeyAction {

    private final VisibilityDialog window;

    public WindowHotkey(VisibilityDialog window) {
        this.window = window;
    }

    @Override
    public void execute() {
        if (window == null) return;
        SwingUtilities.invokeLater(() -> window.setVisible(!window.isVisible()));
    }

}
