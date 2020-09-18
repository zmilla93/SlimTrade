package com.slimtrade.core.debug;

import com.slimtrade.App;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Keeps a timestamped log for the last 5 runs of the program.
 */

public class Debugger {

    public static final String DIRECTORY = App.saveManager.INSTALL_DIRECTORY + File.separator + "logs";
    private final String reg = "log_\\d{4}-\\d{2}-\\d{2}_\\d{2}'\\d{2}'\\d{2}\\.txt";
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private String path;
    boolean enabled = true;

    private FileWriter fw;
    private BufferedWriter bw;
    private final DebugFile debugFile;

    public Debugger() {
        this(null);
    }

    public Debugger(String timestamp) {
        boolean append = false;
        if (timestamp == null) {
            debugFile = new DebugFile();
        } else {
            debugFile = new DebugFile(timestamp);
            append = true;
        }
        try {
            path = debugFile.getFilePath();
            fw = new FileWriter(path, append);
            bw = new BufferedWriter(fw);
        } catch (IOException e) {
            enabled = false;
            e.printStackTrace();
        }
        if (timestamp == null) {
            clearOldFiles();
        }
    }

    public String getTimestamp() {
        return debugFile.getTimestamp();
    }

    public void log(String message) {
        System.out.println(message);
        if (!enabled) return;
        try {
            String timestamp = timeFormat.format(new Date());
            bw.write(timestamp + " | " + message + "\n");
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void log(Object[] objects) {
        for (Object o : objects) {
            log(o.toString());
        }
    }

    public void close() {
        try {
            bw.close();
            fw.close();
            enabled = false;
        } catch (IOException ignored) {

        }
    }

    private void clearOldFiles() {
        File dir = new File(DIRECTORY);
        File[] files = null;
        if (dir.exists() && dir.isDirectory()) {
            files = dir.listFiles();
        }
        ArrayList<Date> dates = new ArrayList<>();
        if (files != null) {
            for (File f : files) {
                if (!f.getName().matches(reg)) {
                    continue;
                }
                DebugFile debugFile = new DebugFile(f);
                Date date;
                try {
                    date = timeFormat.parse(debugFile.getTimestamp());
                    dates.add(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        final int MAX_ATTEMPTS = 20;
        int i = 0;
        while (dates.size() > 5 && i < MAX_ATTEMPTS) {
            Date minDate = Collections.min(dates);
            String minDateString = timeFormat.format(minDate);
            DebugFile debugFile = new DebugFile(minDateString);
            File file = new File(debugFile.getFilePath());
            if (file.delete()) {
                log("Deleted old log file: " + debugFile.getFilePath());
                dates.remove(minDate);
            } else {
                log("Failed to delete log file: " + debugFile.getFilePath());
            }
            i++;
        }
    }

}
