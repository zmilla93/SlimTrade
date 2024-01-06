package com.slimtrade.core.saving.legacy;

import com.slimtrade.core.utility.MacroButton;
import com.slimtrade.gui.chatscanner.ChatScannerEntry;

import java.util.ArrayList;

public class LegacyScannerSave0 {

    public LegacyScannerMessage[] messages = new LegacyScannerMessage[0];

    protected static class LegacyScannerMessage {
        public String name;
        public String searchTermsRaw;
        public String ignoreTermsRaw;
        public ArrayList<LegacySettingsSaveFile_0.LegacyMacroButton> macroButtons;

        public ChatScannerEntry toChatScannerEntry() {
            ArrayList<MacroButton> newButtons = new ArrayList<>();
            for (LegacySettingsSaveFile_0.LegacyMacroButton button : macroButtons) {
                newButtons.add(button.toMacroButton());
            }
            return new ChatScannerEntry(name, searchTermsRaw, ignoreTermsRaw, newButtons);
        }
    }

}
