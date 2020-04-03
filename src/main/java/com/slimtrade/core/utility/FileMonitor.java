package com.slimtrade.core.utility;

import com.slimtrade.App;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.logging.Level;

public class FileMonitor {

    private WatchService watcher;
    private WatchKey clientKey;
    private Thread monitor;

    public FileMonitor() {

    }

    public void startMonitor() {
        try {
            watcher = FileSystems.getDefault().newWatchService();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Path path;
        File file = new File(App.saveManager.saveFile.clientPath);
        if(file.exists() && file.isFile()) {
            path = Paths.get(file.getParent());
        } else {
            App.logger.log(Level.WARNING, "Bad client path");
            return;
        }

        if (clientKey != null) {
            clientKey.cancel();
            clientKey = null;
        }
//		System.out.println("CLIENT KEY ::: " + clientKey);
        try {
            path.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);
            clientKey = path.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);
        } catch (IOException | ClosedWatchServiceException e) {
            e.printStackTrace();
            return;
        }
        monitor = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    WatchKey key;
                    try {
                        key = watcher.take();
                    } catch (InterruptedException e) {
                        try {
                            watcher.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                        break;
                    }
                    for (WatchEvent<?> event : key.pollEvents()) {
                        if (event.context().toString().toLowerCase().equals("client.txt")) {
                            App.chatParser.update();
                            if (event.kind() == StandardWatchEventKinds.OVERFLOW) {
                                System.err.println("Overflow");
                                continue;
                            }
                        }
                    }
                    key.reset();
                }
            }
        });
        monitor.start();

    }

    public void stopMonitor() {
        try {
            monitor.interrupt();
            monitor.join();
            clientKey.cancel();
        } catch (InterruptedException | NullPointerException e) {
            // e.printStackTrace();
            return;
        }
    }

}
