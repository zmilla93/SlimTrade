package com.slimtrade.core.saving.savefiles;

/**
 * All save files extend this class to handle versioning.
 */
public abstract class AbstractSaveFile {

    /**
     * The version of the save file that is actually saved to disk.
     */
    public int saveFileVersion;

}
