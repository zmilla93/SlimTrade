package com.slimtrade.core.hotkeys;

import com.slimtrade.core.managers.QuickPasteManager;

public class QuickPasteHotkey implements IHotkeyAction{
    @Override
    public void execute() {
        QuickPasteManager.attemptHotkeyQuickPaste();
    }
}
