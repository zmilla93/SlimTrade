package github.zmilla93.modules.stopwatch;

import java.util.Formatter;

public class Stopwatch {

    private static final Formatter formatter = new Formatter();
    private static long startTime;

    public static void start() {
        startTime = System.nanoTime();
    }

    public static float getElapsedSeconds() {
        long elapsed = System.nanoTime() - startTime;
        elapsed /= 1000000;
        return elapsed / 1000f;
    }

    public static float getElapsedMilliSeconds() {
        long elapsed = System.nanoTime() - startTime;
        elapsed /= 1000000;
        return elapsed;
    }

}
