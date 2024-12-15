package github.zmilla93.core.poe;

import github.zmilla93.core.managers.SaveManager;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * An enum for knowing which version of Path of Exile is being referenced.
 * Also contains utility functions for dealing with game disparity.
 */
public enum Game {

    PATH_OF_EXILE_1("Path of Exile", "Path of Exile 1", "poe1"), PATH_OF_EXILE_2("Path of Exile 2", null, "poe2");

    private final String name;
    private final String explicitName;
    public final String assetsFolderName;

    /**
     * Returns "Path of Exile 1" instead of "Path of Exile" when used on POE1
     */
    public String getExplicitName() {
        return explicitName;
    }

    Game(String name, String explicitName, String assetsFolderName) {
        this.name = name;
        this.explicitName = explicitName;
        this.assetsFolderName = assetsFolderName;
    }

    public boolean isPoe1() {
        return this == Game.PATH_OF_EXILE_1;
    }

    /**
     *
     */
    public boolean isClientPathValid() {
        // FIXME : Should this check isInstalled first?
        String pathString;
        if (isPoe1()) pathString = SaveManager.settingsSaveFile.data.installFolderPoe1;
        else pathString = SaveManager.settingsSaveFile.data.installFolderPoe2;
        if (pathString == null) return false;
        Path poeFolder = Paths.get(pathString);
        Path clientPath = poeFolder.resolve(Paths.get(SaveManager.POE_LOG_FOLDER_NAME, SaveManager.POE_LOG_FOLDER_NAME));
        return clientPath.toFile().exists();
    }

    @Override
    public String toString() {
        return name;
    }

}
