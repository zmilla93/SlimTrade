package com.slimtrade.core.update;

import com.slimtrade.App;
import com.slimtrade.core.References;
import com.slimtrade.gui.dialogs.DownloaderFrame;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class UpdateManager {

    private final Patcher patcher = new Patcher(App.debugger);
    private boolean updateChecked = false;
    private boolean updateAvailable = false;
    private final String fileName = References.APP_NAME + ".jar";
    private String latestVersion;
    private ArrayList<ReleaseData> releases;

    // TODO : Switch to main repo
    public static final String TARGET_REPO = References.APP_NAME.toLowerCase() + "-tester";
    private String downloadURL = "https://github.com/" + References.AUTHOR_NAME + "/" + TARGET_REPO + "/releases/download/%tag%/" + fileName;

    public void update() {
        String version = patcher.getLatestVersion() == null ? App.versionTag : patcher.getLatestVersion();
        downloadURL = downloadURL.replaceAll("%tag%", version);
        DownloaderFrame downloaderFrame = new DownloaderFrame(References.APP_NAME, version);
        downloaderFrame.setVisible(true);
        patcher.downloadFile(fileName, downloadURL, downloaderFrame);
        downloaderFrame.dispose();
        runProcess(App.saveManager.INSTALL_DIRECTORY + File.separator + fileName, "patch");
    }

    public void patch() {
        String src = App.saveManager.INSTALL_DIRECTORY + File.separator + fileName;
        String dest = App.launcherPath;
        patcher.copyFile(src, dest);
        runProcess(dest, "clean", "patchNotes");
    }

    public void clean() {
        patcher.deleteFile(File.separator + fileName);
        App.debugger.log("Update process complete.");
    }

    public boolean isUpdateAvailable() {
        if (!updateChecked) {
            updateAvailable = patcher.isUpdateAvailable();
            latestVersion = patcher.getLatestVersion();
            updateChecked = true;
        }
        return updateAvailable;
    }

    public String getVersionTag() {
        return patcher.getLatestVersion();
    }

    public void setLatestVersion(String version) {
        latestVersion = version;
    }

    public String getLatestVersion() {
        return latestVersion;
    }

    public ArrayList<ReleaseData> getReleaseData() {
        if (releases == null) {
            fetchReleaseData();
        }
        return releases;
    }

    public boolean fetchReleaseData() {
//        String version;
        releases = new ArrayList<>();
        BufferedReader br = null;
        InputStream is = null;
        try {
            is = new URL("https://api.github.com/repos/" + References.AUTHOR_NAME + "/" + TARGET_REPO + "/releases").openStream();
        } catch (IOException e) {
            return false;
        }
        if (is == null) {
            // TODO : This will catch rate limit
//            App.debugger.log("NULL::::");
            return false;
        }
        try {
            br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();
            try {
                while (br.ready()) {
                    builder.append(br.readLine());
                }
            } catch (IOException e) {
                App.debugger.log("Error while fetching latest version : " + e.getMessage());
                return false;
            }
            System.out.println("STR : " + builder.toString());
            JSONArray json = new JSONArray(builder.toString());
            for (Object o : json) {
                if (o instanceof JSONObject) {
                    JSONObject obj = (JSONObject) o;
                    releases.add(new ReleaseData(obj));
                }
            }
            return true;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ignored) {

                }
            }
        }
    }

    public void runProcess(String... args) {
        ArrayList<String> launchArgs = new ArrayList<>();
        launchArgs.add("java");
        launchArgs.add("-jar");
        launchArgs.addAll(Arrays.asList(args));
        if (App.versionTag != null) {
            launchArgs.add("versionTag:" + App.versionTag);
        }
        if (App.launcherPath != null) {
            launchArgs.add("launcher:" + App.launcherPath);
        }
        if (App.debuggerTimestamp != null) {
            launchArgs.add("debugger:" + App.debuggerTimestamp);
        }
        App.debugger.log("Running Process: " + Arrays.toString(launchArgs.toArray()) + "\n");
        App.debugger.close();
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(launchArgs);
            processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
            processBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

}
