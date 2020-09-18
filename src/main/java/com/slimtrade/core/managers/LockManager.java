package com.slimtrade.core.managers;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;

public class LockManager {

    // Internal
    private String installDirectory;
    private static File lockFile;
    private static FileChannel channel;
    private static FileLock lock;

//    private final Debugger debugger;

    public LockManager(String installDirectory) {
        this.installDirectory = installDirectory;
//        debugger = new Debugger();
    }

//    public LockManager(String installDirectory, Debugger debugger) {
//        this.installDirectory = installDirectory;
//        this.debugger = debugger;
//    }

    public boolean isFileLocked(String fileName) {
        File file = new File(installDirectory, fileName);
        if (file.exists()) {
            if (file.delete()) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public boolean tryAndLock(String fileName) {
        try {
            lockFile = new File(installDirectory, fileName);
            if (lockFile.exists()) {
                if (!lockFile.delete()) {
                    return false;
                }
            }
            channel = new RandomAccessFile(lockFile, "rw").getChannel();
            try {
                lock = channel.tryLock();
            } catch (OverlappingFileLockException e) {
                closeLock();
                return false;
            }
            if (lock == null) {
                closeLock();
                return false;
            }
            return true;
        } catch (IOException e) {
            closeLock();
            return false;
        }
    }

    // Close Lock
    public void closeLock() {
        try {
            if (lock != null && lock.isValid()) {
                lock.release();
            }
            if (channel != null) {
                channel.close();
            }
        } catch (IOException e) {
//            debugger.log("Error closing launcher lock file : " + e.getMessage());
//            debugger.log(e.getStackTrace());
        }
    }

    // Delete Lock
    public void deleteLock() {
        if (lockFile != null && lockFile.isFile()) {
            if (!lockFile.delete()) {
//                debugger.log("Failed to delete launcher lock file");
            }
        }
    }

}
