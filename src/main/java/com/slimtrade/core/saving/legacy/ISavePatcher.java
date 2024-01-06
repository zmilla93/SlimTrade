package com.slimtrade.core.saving.legacy;

public interface ISavePatcher {

    boolean requiresPatch();

    boolean patch();

    void applyNewVersion();

    void handleCorruptedFile();

}
