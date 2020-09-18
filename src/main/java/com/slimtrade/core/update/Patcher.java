package com.slimtrade.core.update;

import com.slimtrade.App;
import com.slimtrade.core.References;
import com.slimtrade.core.debug.Debugger;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Patcher {

    // Github Stuff
    private final String GIT_TAG_KEY = "tag_name";
    private final String API_LATEST_VERSION;
    private final String BLANK_DOWNLOAD;

    // Internal
    private String latestVersion;
    private boolean autoUpdate = true;
    private boolean apiRateLimited = false;
    private boolean coreFileExists;
    private String launcherPath;
    private String launcherFileName;
    private JSONObject launcherJSON;
    private final int MAX_ATTEMPTS = 5;

    private Debugger debugger;


    public Patcher(Debugger debugger) {
        this.debugger = debugger;
        API_LATEST_VERSION = "https://api.github.com/repos/" + References.AUTHOR_NAME + "/" + UpdateManager.TARGET_REPO + "/releases/latest";
        BLANK_DOWNLOAD = "https://github.com/" + References.AUTHOR_NAME + "/" + UpdateManager.TARGET_REPO + "/releases/download/%tag%/%file%";

        // Save launcher path
        try {
            launcherPath = URLDecoder.decode(new File(Patcher.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath(), "UTF-8");
            File launcherFile = new File(launcherPath);
            launcherFileName = launcherFile.getName();
        } catch (URISyntaxException | UnsupportedEncodingException e) {
            debugger.log("Failed to get launch path : " + e.getMessage());
            debugger.log(e.getStackTrace());
        }
    }

    public boolean isUpdateAvailable() {
        debugger.log("Checking for update...");
        debugger.log("Current Version: " + References.getAppVersion());
        latestVersion = fetchLatestVersion();
        if (latestVersion == null) {
            apiRateLimited = true;
            debugger.log("API rate exceeded.");
            return false;
        }
        debugger.log("Latest Version: " + latestVersion);
        if (!latestVersion.matches(References.getAppVersion())) {
            debugger.log("New Version Available : " + latestVersion);
            return true;
        }
        debugger.log("No update found.");
        return false;
    }

    private String fetchLatestVersion() {
        String version;
        BufferedReader br = null;
        InputStream is;
        try {
            is = new URL(API_LATEST_VERSION).openStream();
        } catch (IOException e) {
            return null;
        }
        try {
            br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();
            try {
                while (br.ready()) {
                    builder.append(br.readLine());
                }
            } catch (IOException e) {
                debugger.log("Error while fetching latest version : " + e.getMessage());
            }
            JSONObject json = new JSONObject(builder.toString());
            version = json.getString(GIT_TAG_KEY);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    debugger.log(e.getStackTrace());
                }
            }
        }
        return version;
    }

    public boolean downloadFile(String fileName, String urlString, IDownloadTracker tracker) {
        int attempts = 1;
        while (attempts <= MAX_ATTEMPTS) {
            try {
                debugger.log("Downloading '" + fileName + "' from '" + urlString + "'...");
                URL url = new URL(urlString);
                HttpURLConnection httpConnection = (HttpURLConnection) (url.openConnection());
                long fileSize = httpConnection.getContentLength();
                BufferedInputStream in = new BufferedInputStream(httpConnection.getInputStream());
                FileOutputStream fos = new FileOutputStream(App.saveManager.INSTALL_DIRECTORY + File.separator + fileName);
                BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
                byte[] data = new byte[1024];
                long downloadedFileSize = 0;
                int i = 0;
                while ((i = in.read(data, 0, 1024)) >= 0) {
                    downloadedFileSize += i;
                    final int currentProgress = (int) ((((double) downloadedFileSize) / ((double) fileSize)) * 100000d);
                    tracker.downloadPercentCallback(currentProgress);
                    bout.write(data, 0, i);
                }
                bout.close();
                in.close();
                debugger.log("Download complete.");
                return true;
            } catch (IOException e) {
                if (attempts == MAX_ATTEMPTS) {
                    debugger.log("Error downloading file from '" + urlString + "'");
                    return false;
                } else {
                    tracker.textCallback("Download failed, retrying...");
                    debugger.log("Download failed, retrying... (" + attempts + ")");
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                    attempts++;
                }
            }
        }
        return false;
    }

    public boolean deleteFile(String fileName) {
        File file = new File(App.saveManager.INSTALL_DIRECTORY + File.separator + fileName);
        if (!file.exists()) {
            return true;
        }
        debugger.log("Deleting file: " + file.getPath());
        int attempts = 0;
        while (file.exists() && attempts < MAX_ATTEMPTS && !file.delete()) {
            debugger.log("Failed to delete temp launcher file");
            debugger.log("Retrying (" + (attempts + 1) + ")...");
            attempts++;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        boolean exists = file.exists();
        if (exists) {
            debugger.log("Failed to delete file.");
        } else {
            debugger.log("File deleted successfully.");
        }
        return exists;
    }

    public boolean copyFile(String sourceName, String destName) {
        File destFile = new File(destName);
        if (destFile.exists()) {
            if (!destFile.delete()) {
                return false;
            }
        }
        Path src = new File(sourceName).toPath();
        Path dest = new File(destName).toPath();
        int attempts = 1;
        debugger.log("Moving file '" + sourceName + "' to '" + destName + "'...");
        while (attempts <= MAX_ATTEMPTS) {
            try {
                Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
                debugger.log("File moved successfully.");
                return true;
            } catch (IOException e) {
                if (attempts < MAX_ATTEMPTS) {
                    System.out.println("Failed to move file, retrying...");
                    attempts++;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                } else {
                    debugger.log("Failed to move and overwrite file!");
                    debugger.log("Source: " + sourceName);
                    debugger.log("Destination: " + destName);
                    debugger.log(e.getStackTrace());
                    return false;
                }
            }
        }
        return false;
    }

    // Getters

    public boolean isAutoUpdate() {
        return autoUpdate;
    }

    public String getLatestVersion() {
        return latestVersion;
    }

    public boolean isApiRateLimited() {
        return apiRateLimited;
    }

    public boolean isCoreFileExists() {
        return coreFileExists;
    }

}
