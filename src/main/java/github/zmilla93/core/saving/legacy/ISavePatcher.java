package github.zmilla93.core.saving.legacy;

public interface ISavePatcher {

    String FAILED_TO_LOAD = "Failed to load legacy save file from disk.";

    boolean requiresPatch();

    boolean patch();

    void applyNewVersion();

    void handleCorruptedFile();

    String getErrorMessage();

}
