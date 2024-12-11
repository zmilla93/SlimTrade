package github.zmilla93.modules.filemonitor;

import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;

/**
 * Enum of events for creating, modifying, and deleting files.
 * Acts as a simplified version of Java's {@link StandardWatchEventKinds}.
 */
public enum FileEventType {

    OVERFLOW, CREATE, MODIFY, DELETE;

    public static FileEventType fromWatchEventKind(WatchEvent.Kind<Path> kind) {
        if (kind == StandardWatchEventKinds.ENTRY_CREATE) return CREATE;
        if (kind == StandardWatchEventKinds.ENTRY_MODIFY) return MODIFY;
        if (kind == StandardWatchEventKinds.ENTRY_DELETE) return DELETE;
        return OVERFLOW;
    }

}
