package github.zmilla93.core.saving.legacy.savefiles;

import github.zmilla93.core.utility.MacroButton;
import github.zmilla93.gui.chatscanner.ChatScannerEntry;
import github.zmilla93.modules.saving.AbstractSaveFile;

import java.util.ArrayList;

public class LegacyScannerSave0 extends AbstractSaveFile {

    public LegacyScannerMessage[] messages = new LegacyScannerMessage[0];

    public static class LegacyScannerMessage {
        public String name;
        public String searchTermsRaw;
        public String ignoreTermsRaw;
        public ArrayList<LegacySettingsSave0.LegacyMacroButton> macroButtons;

        public ChatScannerEntry toChatScannerEntry() {
            ArrayList<MacroButton> newButtons = new ArrayList<>();
            for (LegacySettingsSave0.LegacyMacroButton button : macroButtons) {
                newButtons.add(button.toMacroButton());
            }
            return new ChatScannerEntry(name, searchTermsRaw, ignoreTermsRaw, newButtons, true, true, false);
        }
    }

    @Override
    public int getCurrentTargetVersion() {
        return 0;
    }

}
