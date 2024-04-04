package com.slimtrade.modules.updater;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    private static String logFile;
    private static String directory;
    private static boolean isOpen = false;

    private static final SimpleDateFormat fileNameFormatter = new SimpleDateFormat("yyyy-MM-dd_HH'h'mm'm'ss's'");
    private static final SimpleDateFormat timestampFormatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static final String regString = FILE_PREFIX + "(?<year>\\d+)-(?<month>\\d+)-(?<day>\\d+)_(?<hour>\\d+)h(?<minute>\\d+)m(?<second>\\d+)s\\.txt";

    public static void open(String appDirectory, String[] args) {
        directory = UpdateUtil.cleanFileSeparators(appDirectory) + SUBFOLDER;
        if (!UpdateUtil.validateDirectory(directory)) {
            System.err.println("Failed to validate logging directory: " + directory);
            return;
        }
        boolean newFile = false;
        for (String arg : args) {
            if (arg.startsWith(LOG_ARG_PREFIX)) {
                logFile = arg.replaceFirst(LOG_ARG_PREFIX, "");
                break;
            }
        }
        if (logFile == null) {
            String time = fileNameFormatter.format(System.currentTimeMillis());
            logFile = directory + File.separator + FILE_PREFIX + time + ".txt";
            newFile = true;
        }
        try {
            if (newFile) {
                writer = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(Paths.get(logFile)), StandardCharsets.UTF_8));
            } else
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logFile, true), StandardCharsets.UTF_8));
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
        return LOG_ARG_PREFIX + logFile;
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
        File dir = new File(directory);
        File[] files = dir.listFiles();
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
            String path = directory + File.separator + FILE_PREFIX + fileTimestamp + ".txt";
            File file = new File(path);
            if (file.delete()) log("Deleted old log file: " + path);
            else log("Failed to delete file: " + path);
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
