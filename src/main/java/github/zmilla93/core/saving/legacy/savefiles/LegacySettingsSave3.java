package github.zmilla93.core.saving.legacy.savefiles;

import github.zmilla93.core.saving.savefiles.AbstractSaveFile;

public class LegacySettingsSave3 extends AbstractSaveFile {

    public String clientPath;

    @Override
    public int getCurrentTargetVersion() {
        return 0;
    }

}
