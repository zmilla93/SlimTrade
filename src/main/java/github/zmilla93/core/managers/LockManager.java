package github.zmilla93.core.managers;

import github.zmilla93.modules.updater.ZLogger;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.nio.file.Files;
import java.nio.file.Path;

// FIXME : Switch to using Paths
public class LockManager {

    private final Path installDirectory;
    private final String fileName;
    private File lockFile;
    private FileLock lock;
    private FileChannel channel;
    private static final int MAX_DELETE_ATTEMPTS = 5;
    private static final int DELAY_BETWEEN_DELETE_ATTEMPTS_MS = 100;

    public LockManager(Path installDirectory, String fileName) {
        this.installDirectory = installDirectory;
        this.fileName = fileName;
    }

    public boolean tryAndLock() {
        try {
            Files.createDirectories(installDirectory);
            lockFile = installDirectory.resolve(fileName).toFile();
            if (lockFile.exists()) {
                boolean deleteSuccess = false;
                for (int i = 0; i < MAX_DELETE_ATTEMPTS; i++) {
                    if (lockFile.delete()) {
                        deleteSuccess = true;
                        break;
                    }
                    try {
                        Thread.sleep(DELAY_BETWEEN_DELETE_ATTEMPTS_MS);
                    } catch (InterruptedException ignore) {
                    }
                }
                if (!deleteSuccess) return false;
            }
            //noinspection resource
            channel = new RandomAccessFile(lockFile, "rw").getChannel();
            try {
                lock = channel.tryLock();
            } catch (OverlappingFileLockException e) {
                return false;
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void closeLock() {
        try {
            if (channel != null) channel.close();
            if (lock != null) {
                if (lock.isValid()) lock.release();
                if (!lockFile.delete()) ZLogger.err("Failed to delete lock file!");
            }
        } catch (IOException ignore) {
        }
    }

}
