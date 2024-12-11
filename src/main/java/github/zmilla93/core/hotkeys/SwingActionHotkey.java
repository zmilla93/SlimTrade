package github.zmilla93.core.hotkeys;

import javax.swing.*;

/**
 * Used to accept lambda expressions and run them on the Event Dispatch Thread.
 */
public class SwingActionHotkey implements IHotkeyAction {

    private final Runnable action;

    public SwingActionHotkey(Runnable action) {
        this.action = action;
    }

    @Override
    public void execute() {
        SwingUtilities.invokeLater(action);
    }

}
