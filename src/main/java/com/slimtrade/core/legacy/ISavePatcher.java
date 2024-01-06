package com.slimtrade.core.legacy;

public interface ISavePatcher {

    boolean requiresPatch();

    boolean patch();

}
