package com.slimtrade.core.managers;

import com.slimtrade.modules.updater.ZLogger;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;

// FIXME : Switch to using Paths
public class LockManager {

    private final String installDirectory;
    private final String fileName;
    private File lockFile;
    private FileLock lock;
    private FileChannel channel;

    public LockManager(String installDirectory, String fileName) {
        this.installDirectory = installDirectory;
        this.fileName = fileName;
    }

    public boolean tryAndLock() {
        try {
            File directory = new File(installDirectory);
            if (!directory.exists()) {
                if (!directory.mkdirs()) return false;
            }
            lockFile = new File(installDirectory, fileName);
            if (lockFile.exists())
                if (!lockFile.delete()) return false;
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
