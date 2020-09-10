package com.slimtrade.core.utility;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Basic logging system.
 */

public class Debugger {

    private FileWriter fw;
    private BufferedWriter bw;
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    boolean enabled = false;

    public Debugger() {

    }

    public Debugger(String path, boolean append) {
        enabled = true;
        try {
            fw = new FileWriter(path, append);
            bw = new BufferedWriter(fw);
        } catch (IOException e) {
            enabled = false;
            e.printStackTrace();
        }
    }

    public void log(String message) {
        System.out.println(message);
        if (!enabled) return;
        try {
            String timestamp = timeFormat.format(new Date());
            bw.write(timestamp + " | " + message + "\n");
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
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }

}
