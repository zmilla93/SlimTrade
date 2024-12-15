package github.zmilla93.modules.filetailing;

import github.zmilla93.core.utility.ZUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Simple file tailer implementation.
 */
public class FileTailer implements Runnable {

    private final FileTailerListener listener;
    private final int delay;
    private final boolean end;

    private final BufferedReader reader;
    private boolean running;
    private boolean loaded;

    public FileTailer(Path path, boolean isPathRelative, FileTailerListener listener, int delay, boolean end) {
        this.listener = listener;
        this.delay = delay;
        this.end = end;
        reader = ZUtil.getBufferedReader(path, isPathRelative);
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
    public static FileTailer createTailer(Path path, boolean isPathRelative, FileTailerListener listener, int delay, boolean end) {
        FileTailer tailer = new FileTailer(path, isPathRelative, listener, delay, end);
        launchThread(tailer);
        return tailer;
    }

    private static void launchThread(FileTailer tailer) {
        Thread thread = new Thread(tailer);
        thread.setDaemon(true);
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
