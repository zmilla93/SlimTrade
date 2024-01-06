package com.slimtrade.core.saving;

public interface ISavePatcher {

    boolean requiresPatch();

    boolean patch();

    void applyNewVersion();

}
