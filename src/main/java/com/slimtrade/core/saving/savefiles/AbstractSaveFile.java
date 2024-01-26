package com.slimtrade.core.saving.savefiles;

/**
 * All save files extend this class to handle versioning.
 * Requires an extra function to handle legacy save files that had no version field.
 */
public abstract class AbstractSaveFile {

    /**
     * The version of the save file that is actually saved to disk.
     */
    public int saveFileVersion;

    /**
     * Override this to set the save file version. Applied to saveFileVersion upon saving.
     *
     * @return file version
     */
    public abstract int getTargetFileVersion();

}
