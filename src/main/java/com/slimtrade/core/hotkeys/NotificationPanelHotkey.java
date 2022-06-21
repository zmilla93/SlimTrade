package com.slimtrade.core.hotkeys;

import com.slimtrade.core.data.PasteReplacement;
import com.slimtrade.core.utility.MacroButton;
import com.slimtrade.core.utility.POEInterface;
import com.slimtrade.gui.messaging.NotificationPanel;

import javax.swing.*;

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
