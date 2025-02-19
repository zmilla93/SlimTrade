package github.zmilla93.core.saving.legacy.savefiles;

import github.zmilla93.modules.saving.AbstractSaveFile;

public class LegacySettingsSave4 extends AbstractSaveFile {

    public int initGameDirectories;
    public int initUsingStashFolders;
    public boolean notInstalledPoe1;
    public boolean notInstalledPoe2;
    public String installFolderPoe1;
    public String installFolderPoe2;
    public boolean usingStashFoldersPoe1;
    public boolean usingStashFoldersPoe2;

    @Override
    public int getCurrentTargetVersion() {
        return 0;
    }

}
