package com.slimtrade.core.update;

import com.slimtrade.App;
import com.slimtrade.core.References;
import com.slimtrade.core.utility.TradeOffer;
import com.slimtrade.enums.MessageType;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.dialogs.DownloaderFrame;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class UpdateManager {

    public final Patcher patcher = new Patcher(App.debugger);
    private boolean updateChecked = false;
    private boolean updateAvailable = false;
    private final String fileName = References.APP_NAME + ".jar";
    private String latestVersion;
    private ArrayList<ReleaseData> releases;
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

//    public static final String TARGET_REPO = References.APP_NAME.toLowerCase() + "-tester";
    public static final String TARGET_REPO = References.APP_NAME.toLowerCase();
    private String downloadURL = "https://github.com/" + References.AUTHOR_NAME + "/" + TARGET_REPO + "/releases/download/%tag%/" + fileName;
    private DownloaderFrame downloaderFrame;

    public void update() {
        String version = patcher.getLatestVersion() == null ? App.updateTargetVersion : patcher.getLatestVersion();
        if (version == null) {
            runProcess(App.launcherPath);
        }
        assert version != null;
        downloadURL = downloadURL.replaceAll("%tag%", version);
        try {
            SwingUtilities.invokeAndWait(() -> {
                downloaderFrame = new DownloaderFrame(References.APP_NAME, version);
                downloaderFrame.setVisible(true);
            });
        } catch (InterruptedException | InvocationTargetException e) {
            App.debugger.log(e.getStackTrace());
            runProcess(App.launcherPath);
        }
        boolean downloadResult = patcher.downloadFile(fileName, downloadURL, downloaderFrame);
        try {
            SwingUtilities.invokeAndWait(() -> {
                downloaderFrame.dispose();
            });
        } catch (InterruptedException | InvocationTargetException e) {
            App.debugger.log(e.getStackTrace());
            runProcess(App.launcherPath);
        }
        if (downloadResult) {
            runProcess(App.saveManager.INSTALL_DIRECTORY + File.separator + fileName, "patch");
        } else {
            runProcess(App.launcherPath, "clean");
        }
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
        return isUpdateAvailable(false);
    }

    public boolean isUpdateAvailable(boolean clearCache) {
        if (!updateChecked || clearCache) {
            updateAvailable = patcher.isUpdateAvailable();
            latestVersion = patcher.getLatestVersion();
            updateChecked = true;
        }
        return updateAvailable;
    }

    public String getVersionTag() {
        return patcher.getLatestVersion();
    }

//    public void setLatestVersion(String version) {
//        latestVersion = version;
//    }

//    public String getLatestVersion() {
//        return latestVersion;
//    }

    public ArrayList<ReleaseData> getReleaseData() {
        if (releases == null) {
            releases = fetchReleaseData();
        }
        return releases;
    }

    public ArrayList<ReleaseData> fetchReleaseData() {
        ArrayList<ReleaseData> releases = new ArrayList<>();
        BufferedReader br = null;
        InputStream is;
        try {
            is = new URL("https://api.github.com/repos/" + References.AUTHOR_NAME + "/" + References.APP_NAME + "/releases").openStream();
        } catch (IOException e) {
            return releases;
        }
        if (is == null) {
            // This catches rate limit
            return releases;
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
                return releases;
            }
            JSONArray json = new JSONArray(builder.toString());
            for (Object o : json) {
                if (o instanceof JSONObject) {
                    JSONObject obj = (JSONObject) o;
                    releases.add(new ReleaseData(obj));
                }
            }
            return releases;
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
        if (App.launcherPath != null) {
            launchArgs.add("launcher:" + App.launcherPath);
        }
        if (App.debuggerTimestamp != null) {
            launchArgs.add("debugger:" + App.debuggerTimestamp);
        }
        App.debugger.log("Running Process: " + Arrays.toString(launchArgs.toArray()) + "\n");
        App.debugger.close();
        App.lockManager.closeLock();
        App.lockManager.deleteLock();
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

    public void runDelayedUpdateCheck() {
        scheduler.schedule(() -> {
            App.update = App.updateManager.isUpdateAvailable(true);
            System.out.println("CHECKING UPDATE!!");
            System.out.println("Latest Version : " + App.updateManager.patcher.getLatestVersion());
            if (App.update) {
                FrameManager.optionsWindow.showUpdateButton();
                FrameManager.messageManager.addMessage(new TradeOffer(MessageType.NOTIFICATION, "Update Available!", "Check the options menu to install."));
            } else {
                runDelayedUpdateCheck();
            }
        }, 24, TimeUnit.HOURS);
    }

    public void runUpdateProcess() {
        String version = App.updateManager.patcher.getLatestVersion();
        if (version != null) {
            runProcess(App.launcherPath, "update:" + version);
        } else {
            runProcess(App.launcherPath, "update");
        }
    }

}
