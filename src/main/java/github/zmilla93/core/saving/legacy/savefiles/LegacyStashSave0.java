package github.zmilla93.core.saving.legacy.savefiles;

import github.zmilla93.modules.saving.AbstractSaveFile;

public class LegacyStashSave0 extends AbstractSaveFile {

    public int gridX, gridY, gridWidth, gridHeight;

    @Override
    public int getCurrentTargetVersion() {
        return -1;
    }

}
