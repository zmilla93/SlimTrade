package com.slimtrade.core.debug;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DebugFile {

    private final String filePath;
    private final String timestamp;
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public DebugFile() {
        timestamp = timeFormat.format(new Date());
        String fileName = "log_" + timestamp.replaceAll("/", "-").replaceAll(":", "'").replaceAll(" ", "_");
        filePath = Debugger.DIRECTORY + File.separator + fileName + ".txt";
    }

    public DebugFile(String timestamp) {
        this.timestamp = timestamp;
        String fileName = "log_" + timestamp.replaceAll("/", "-").replaceAll(":", "'").replaceAll(" ", "_");
        filePath = Debugger.DIRECTORY + File.separator + fileName + ".txt";
    }

    public DebugFile(File file) {
        String fileName = file.getName();
        this.timestamp = fileName.replaceFirst("log_", "").replaceAll("-", "/").replaceAll("'", ":").replaceAll("_", " ");
        filePath = file.getPath();
    }

    public String getFilePath() {
        return filePath;
    }

    public String getTimestamp() {
        return timestamp;
    }

}
