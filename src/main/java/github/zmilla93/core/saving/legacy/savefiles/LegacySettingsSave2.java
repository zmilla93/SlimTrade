package github.zmilla93.core.saving.legacy.savefiles;

import github.zmilla93.core.hotkeys.HotkeyData;
import github.zmilla93.core.saving.savefiles.AbstractSaveFile;

public class LegacySettingsSave2 extends AbstractSaveFile {

    public HotkeyData necropolisHotkey;

    @Override
    public int getCurrentTargetVersion() {
        return 0;
    }

}
