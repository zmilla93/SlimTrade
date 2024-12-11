package github.zmilla93.modules.filemonitor;

import java.nio.file.Path;
import java.time.Instant;

/**
 * A file change event representing a file being created, modified, or deleted.
 */
public class FileChangeEvent {

    public final FileEventType eventType;
    public final String fileName;
    public final Path fullPath;
    public final Instant timestamp;

    private final String cleanString;

    public FileChangeEvent(FileEventType eventType, String fileName, Path fullPath, Instant timestamp) {
        this.eventType = eventType;
        this.fileName = fileName;
        this.fullPath = fullPath;
        this.timestamp = timestamp;
        cleanString = "FileChangeEvent(" + eventType + ", " + fullPath + ")";
    }

    @Override
    public String toString() {
        return cleanString;
    }

}
