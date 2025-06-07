package github.zmilla93.core.poe;

import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class GameSettings {

    // FIXME : Move to validator?
    public static final String CLIENT_TXT_NAME = "Client.txt";
    public static final String LOG_FOLDER_NAME = "logs";

    public boolean notInstalled;
    public String installFolder;
    public boolean usingStashFolder;
    // TODO : League

    public final Path getClientPath() {
        if (installFolder == null) return null;
        Path folderPath = Paths.get(installFolder);
        return folderPath.resolve(Paths.get(LOG_FOLDER_NAME, CLIENT_TXT_NAME));
    }

    // TODO
    @Deprecated // FIXME : Switch to or combine with PoeClientPathCheck
    public boolean isClientPathValid() {
        if (installFolder == null) return false;
        Path folderPath = Paths.get(installFolder);
        Path clientPath = folderPath.resolve(Paths.get(LOG_FOLDER_NAME, CLIENT_TXT_NAME));
        return clientPath.toFile().exists();
    }

    public abstract Game game();

    public abstract boolean isPoe1();

}
