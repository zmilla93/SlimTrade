package com.slimtrade.modules.filetailing;

import com.slimtrade.modules.updater.ZLogger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * Simple file tailer implementation.
 */
public class FileTailer implements Runnable {

    private final FileTailerListener listener;
    private final int delay;
    private final boolean end;

    private BufferedReader reader;
    private boolean running;
    private boolean loaded;

    public FileTailer(File file, FileTailerListener listener, int delay, boolean end) throws IOException {
        this(new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8), listener, delay, end);
    }

    public FileTailer(InputStreamReader inputStream, FileTailerListener listener, int delay, boolean end) {
        this.listener = listener;
        this.delay = delay;
        this.end = end;
        reader = new BufferedReader(inputStream);
    }

    public void stop() {
        running = false;
    }

    public boolean isLoaded() {
        return loaded;
    }

    /**
     * Creates a tailer and runs it on a background thread.
     */

    public static FileTailer createTailer(InputStreamReader inputStream, FileTailerListener listener, int delay, boolean end) {
        FileTailer tailer = new FileTailer(inputStream, listener, delay, end);
        launchThread(tailer);
        return tailer;
    }

    public static FileTailer createTailer(File file, FileTailerListener listener, int delay, boolean end) {
        try {
            FileTailer tailer = new FileTailer(file, listener, delay, end);
            launchThread(tailer);
            return tailer;
        } catch (IOException e) {
            ZLogger.err("FileTailer failed to open file: " + file);
            ZLogger.err(e.getStackTrace());
            throw new RuntimeException(e);
        }
    }

    private static void launchThread(FileTailer tailer) {
        Thread thread = new Thread(tailer);
        thread.start();
        tailer.running = true;
    }

    @Override
    public void run() {
        listener.init(this);
        try {
            while (running) {
                while (running && reader.ready()) {
                    String line = reader.readLine();
                    if (loaded || !end) {
                        listener.handle(line);
                    }
                }
                if (!loaded) {
                    loaded = true;
                    listener.onLoad();
                }
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
