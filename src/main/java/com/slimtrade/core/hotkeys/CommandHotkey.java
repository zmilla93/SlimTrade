package com.slimtrade.core.hotkeys;

import com.slimtrade.core.data.PasteReplacement;
import com.slimtrade.core.utility.MacroButton;
import com.slimtrade.core.utility.POEInterface;
import com.slimtrade.gui.messaging.NotificationPanel;

import javax.swing.*;

public class CommandHotkey implements IHotkeyAction {

    private PasteReplacement pasteReplacement;
    private NotificationPanel notificationPanel;
    private MacroButton macroButton;

    public CommandHotkey(MacroButton macroButton, NotificationPanel notificationPanel, PasteReplacement pasteReplacement) {
        this.macroButton = macroButton;
        this.notificationPanel = notificationPanel;
        this.pasteReplacement = pasteReplacement;
    }

    @Override
    public void execute() {
        SwingUtilities.invokeLater(() -> notificationPanel.handleHotkeyMutual(macroButton));
        POEInterface.pasteWithFocus(macroButton.lmbResponse, pasteReplacement);
    }

}
