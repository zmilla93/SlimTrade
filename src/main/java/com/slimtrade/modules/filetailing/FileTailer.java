package com.slimtrade.modules.filetailing;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Simple file tailer implementation.
 */
public class FileTailer implements Runnable {

    private final File file;
    private final FileTailerListener listener;
    private final int delay;
    private final boolean end;

    private BufferedReader reader;
    private boolean running;
    private boolean loaded;

    public FileTailer(File file, FileTailerListener listener, int delay, boolean end) {
        this.file = file;
        this.listener = listener;
        this.delay = delay;
        this.end = end;
        try {
//            reader = new BufferedReader(new FileReader(file));
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            listener.fileNotFound();
            e.printStackTrace();
        }
    }

    public void stop() {
        running = false;
    }

    /**
     * Creates a tailer and runs it on a background thread.
     *
     * @return
     */
    public static FileTailer createTailer(File file, FileTailerListener listener, int delay, boolean end) {
        FileTailer tailer = new FileTailer(file, listener, delay, end);
        Thread thread = new Thread(tailer);
//        thread.setDaemon(true);
        thread.start();
        tailer.running = true;
        return tailer;
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
