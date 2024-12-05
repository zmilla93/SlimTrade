package com.slimtrade.modules.filemonitor;

import java.io.IOException;
import java.nio.file.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import static java.nio.file.StandardWatchEventKinds.*;

/**
 * Monitors a file or directory for create, modify, and delete events.
 * Modify events are automatically debounced, since multiple modify events can occur for the same action.
 * Debounce delay is configurable with setDebounceDelay(), disable with a delay of <= 0.
 */
public class FileMonitor {

    // Threading
    private static final WatchService watcher;
    private static Thread watchThread;
    private static boolean watchThreadIsRunning = false;

    // Map watch keys to the file monitors they represent.
    private static final HashMap<WatchKey, FileMonitor> watchKeyMap = new HashMap<>();

    // Instance Info
    public final Path path;
    private WatchKey watchKey;
    private boolean running = false;
    private final ArrayList<FileChangeListener> listeners = new ArrayList<>();

    // Debouncing
    private static final int DEFAULT_DEBOUNCE_DELAY = 50;
    private int debounceDelay = DEFAULT_DEBOUNCE_DELAY;
    private final Timer timer = new Timer();
    private final HashMap<String, TimerTask> debounceMap = new HashMap<>();

    static {
        try {
            // Setup watch service & monitoring thread.
            watcher = FileSystems.getDefault().newWatchService();
            startWatchThread();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public FileMonitor(String pathString) {
        path = Paths.get(pathString);
    }

    public FileMonitor(String pathString, FileChangeListener listener) {
        this(pathString);
        listeners.add(listener);
    }

    /**
     * Starts monitoring the target file or directory.
     */
    public FileMonitor start() {
        if (running) return this;
        try {
            watchKey = path.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        watchKeyMap.put(watchKey, this);
        running = true;
        return this;
    }

    /**
     * Stops monitoring the target file or directory.
     */
    public void stop() {
        if (!running) return;
        watchKey.cancel();
        watchKeyMap.remove(watchKey);
        watchKey = null;
        running = false;
    }

    /**
     * Sets the delay for debouncing events that have the exact same information.
     *
     * @param delay Delay in milliseconds
     */
    public void setDebounceDelay(int delay) {
        debounceDelay = delay;
    }

    private void handleFileChangeEvent(FileChangeEvent event) {
        if (event.eventType == FileEventType.MODIFY && debounceDelay > 0) {
            // Multiple MODIFY events can occur for a single action, so they need to be debounced.
            if (debounceMap.containsKey(event.fileName)) {
                TimerTask task = debounceMap.get(event.fileName);
                task.cancel();
                timer.purge();
            }
            TimerTask task = createTask(event);
            timer.schedule(task, debounceDelay);
            debounceMap.put(event.fileName, task);
        } else {
            // CREATE and DELETE events don't need to be debounced.
            alertListenersOfFileChange(event);
        }
    }

    private TimerTask createTask(FileChangeEvent event) {
        return new TimerTask() {
            @Override
            public void run() {
                alertListenersOfFileChange(event);
            }
        };
    }

    private void alertListenersOfFileChange(FileChangeEvent event) {
        for (FileChangeListener listener : listeners)
            listener.onFileChanged(event);
    }

    public void addListener(FileChangeListener listener) {
        if (listener == null) return;
        listeners.add(listener);
    }

    public void removeListener(FileChangeListener listener) {
        listeners.remove(listener);
    }

    public void removeAllListeners() {
        listeners.clear();
    }

    public static FileMonitor startNewMonitor(String path) {
        return new FileMonitor(path).start();
    }

    public static FileMonitor startNewMonitor(String path, FileChangeListener listener) {
        return new FileMonitor(path, listener).start();
    }

    private static void startWatchThread() {
        if (watchThreadIsRunning) return;
        watchThread = new Thread(() -> {
            try {
                while (true) {
                    // Loop automatically waits here when queue is empty.
                    WatchKey watchKey = watcher.take();
                    for (WatchEvent<?> watchEvent : watchKey.pollEvents()) {
                        WatchEvent.Kind<?> kind = watchEvent.kind();
                        if (kind == OVERFLOW) continue;
                        @SuppressWarnings("unchecked") // OVERFLOW is the only non WatchEvent<Path>, so this unchecked cast is safe.
                        WatchEvent<Path> pathEvent = (WatchEvent<Path>) watchEvent;
                        FileEventType eventType = FileEventType.fromWatchEventKind(pathEvent.kind());
                        FileMonitor fileMonitor = watchKeyMap.get(watchKey);
                        Path fileName = pathEvent.context();
                        Path fullPath = fileMonitor.path.resolve(fileName);
                        FileChangeEvent fileChangeEvent = new FileChangeEvent(eventType, fileName.toString(), fullPath, Instant.now());
                        fileMonitor.handleFileChangeEvent(fileChangeEvent);
                    }
                    watchKey.reset();
                }
            } catch (InterruptedException ignore) {
                // Exit thread
            }
        });
        watchThread.setDaemon(true);
        watchThread.start();
        watchThreadIsRunning = true;
    }

    private static void stopWatchThread() {
        if (!watchThreadIsRunning) return;
        watchThread.interrupt();
        watchThread = null;
        watchThreadIsRunning = false;
    }

}
