package github.zmilla93.core.hotkeys;

import github.zmilla93.core.data.PasteReplacement;
import github.zmilla93.core.utility.MacroButton;
import github.zmilla93.core.utility.POEInterface;
import github.zmilla93.gui.messaging.NotificationPanel;

import javax.swing.*;

/**
 * Handles running hotkeys for a Notification Panel.
 */
public class NotificationPanelHotkey implements IHotkeyAction {

    private final PasteReplacement pasteReplacement;
    private final NotificationPanel notificationPanel;
    private final MacroButton macroButton;

    public NotificationPanelHotkey(MacroButton macroButton, NotificationPanel notificationPanel, PasteReplacement pasteReplacement) {
        this.macroButton = macroButton;
        this.notificationPanel = notificationPanel;
        this.pasteReplacement = pasteReplacement;
    }

    @Override
    public void execute() {
        SwingUtilities.invokeLater(() -> notificationPanel.handleHotkeyMutual(macroButton));
        POEInterface.runCommand(macroButton.lmbResponse, pasteReplacement);
    }

}
