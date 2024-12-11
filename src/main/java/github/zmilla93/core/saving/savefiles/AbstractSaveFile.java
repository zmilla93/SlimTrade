package github.zmilla93.core.saving.savefiles;

/**
 * All save files extend this class to handle versioning.
 */
public abstract class AbstractSaveFile {

    /**
     * The version of the save file that is actually saved to disk.
     */
    public int saveFileVersion;

    /**
     * The version of the newest save file. Used when creating a new save file.
     */
    public abstract int getCurrentTargetVersion();

}
