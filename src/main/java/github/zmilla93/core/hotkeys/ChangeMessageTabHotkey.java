package github.zmilla93.core.hotkeys;

import github.zmilla93.gui.managers.FrameManager;

import javax.swing.*;

/**
 * When using tabs, changes the selected tab of the message manager.
 */
public class ChangeMessageTabHotkey implements IHotkeyAction {

    private final int change;

    public ChangeMessageTabHotkey(int change) {
        this.change = change;
    }

    @Override
    public void execute() {
        SwingUtilities.invokeLater(() -> FrameManager.messageManager.changeMessageTab(change));
    }

}
