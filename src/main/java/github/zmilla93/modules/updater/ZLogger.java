package github.zmilla93.modules.updater;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * A simple logging implementation that works through program starts.
 */
// FIXME : Add support for legacy log file format?
public class ZLogger {

    private static final String FILE_PREFIX = "log_";
    private static final String SUBFOLDER = "logs";
    private static final String LOG_ARG_PREFIX = "logfile:";
    private static final int MAX_LOG_FILES = 5;

    private static BufferedWriter writer;
    private static String currentLogFileName;
    private static Path logsDirectory;
    private static boolean isOpen = false;

    private static final SimpleDateFormat fileNameFormatter = new SimpleDateFormat("yyyy-MM-dd_HH'h'mm'm'ss's'");
    private static final SimpleDateFormat timestampFormatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static final String regString = FILE_PREFIX + "(?<year>\\d+)-(?<month>\\d+)-(?<day>\\d+)_(?<hour>\\d+)h(?<minute>\\d+)m(?<second>\\d+)s\\.txt";

    // FIXME: Switch to using Path
    public static void open(Path appDirectory, String[] args) {
        logsDirectory = appDirectory.resolve(SUBFOLDER);
        if (!logsDirectory.toFile().exists()) {
            try {
                Files.createDirectories(logsDirectory);
            } catch (IOException e) {
                System.err.println("Failed to create log directory, logger will not run: " + logsDirectory);
                return;
            }
        }
        boolean newFile = false;
        boolean useExistingFile = false;
        Path existingLogFile = null;
        Path newLogFile = null;
        /// Check if the args contain an existing log file to use (logfile:LOG_FILE_NAME)
        for (String arg : args) {
            if (arg.startsWith(LOG_ARG_PREFIX)) {
                currentLogFileName = arg.replaceFirst(LOG_ARG_PREFIX, "");
                existingLogFile = Paths.get(currentLogFileName);
                useExistingFile = existingLogFile.toFile().exists();
                break;
            }
        }
        /// If not using an existing log file, create a new one.
        if (!useExistingFile) {
            String time = fileNameFormatter.format(System.currentTimeMillis());
            currentLogFileName = FILE_PREFIX + time + ".txt";
            newLogFile = logsDirectory.resolve(currentLogFileName);
        }
        try {
            if (useExistingFile) {
//                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logFileName, true), StandardCharsets.UTF_8));
                writer = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(existingLogFile, StandardOpenOption.APPEND), StandardCharsets.UTF_8));

            } else
                writer = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(newLogFile), StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
        isOpen = true;
    }

    public static void err(String message) {
        log(message, true);
    }

    public static void log(String message) {
        log(message, false);
    }

    private static void log(String message, boolean error) {
        if (error) System.err.println(message);
        else System.out.println(message);
        if (writer == null || !isOpen) return;
        try {
            String prefix = timestampFormatter.format(System.currentTimeMillis());
            prefix = prefix + " | ";
            if (error) prefix += "[ERROR] ";
            writer.write(prefix + message + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void log(String[] messages) {
        for (String message : messages)
            log(message);
    }

    public static void log(StackTraceElement[] elements) {
        for (StackTraceElement element : elements)
            log(element.toString());
    }

    public static void err(StackTraceElement[] elements) {
        for (StackTraceElement element : elements)
            err(element.toString());
    }

    public static String getLaunchArg() {
        return LOG_ARG_PREFIX + logsDirectory.resolve(currentLogFileName);
    }

    public static void close() {
        if (writer == null) return;
        try {
            writer.write("\n");
            writer.close();
            writer = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        isOpen = false;
    }

    public static void cleanOldLogFiles() {
        if (!logsDirectory.toFile().exists()) return;
        File[] files = logsDirectory.toFile().listFiles();
        ArrayList<Date> dates = new ArrayList<>();
        if (files == null) return;
        // Convert file names to sortable dates
        for (File file : files) {
            if (!file.getName().matches(regString)) continue;
            Date date;
            try {
                date = timestampFormatter.parse(fileNameToTimestamp(file.getName()));
                dates.add(date);
            } catch (ParseException ignore) {
            }
        }
        final int MAX_FILE_DELETIONS = 20;
        int attempts = 0;
        // Delete old log files
        while (dates.size() > MAX_LOG_FILES && attempts < MAX_FILE_DELETIONS) {
            Date oldestDate = Collections.min(dates);
            String fileTimestamp = fileNameFormatter.format(oldestDate);
            Path path = logsDirectory.resolve(FILE_PREFIX + fileTimestamp + ".txt");
            try {
                Files.delete(path);
            } catch (IOException ignore) {
                System.err.println("Failed to delete log file: " + path);
            }
            dates.remove(oldestDate);
            attempts++;
        }
    }

    private static String fileNameToTimestamp(String fileName) {
        return fileName.replaceFirst(FILE_PREFIX, "")
                .replaceFirst("_", " ")
                .replaceAll("-", "/")
                .replaceAll("[hm]", ":")
                .replaceAll("s", "")
                .replaceAll("\\.txt", "");
    }

}
