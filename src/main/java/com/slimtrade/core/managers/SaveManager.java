package com.slimtrade.core.managers;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.slimtrade.core.saving.savefiles.*;
import com.slimtrade.gui.options.ISaveable;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class SaveManager {

    // Public Info
    public final String savePath;
    public final String stashSavePath;
    public final String overlaySavePath;
    public final String scannerSavePath;
    public final String pinSavePath;
    public final String patcherSavePath;
    public final String INSTALL_DIRECTORY;
    public SettingsSaveFile settingsSaveFile;
    public StashSaveFile stashSaveFile = new StashSaveFile();
    public PinSaveFile pinSaveFile = new PinSaveFile();
    public OverlaySaveFile overlaySaveFile = new OverlaySaveFile();
    public ScannerSaveFile scannerSaveFile = new ScannerSaveFile();

    public ArrayList<String> clientPaths = new ArrayList<>();

    //Internal
    private final String folderWin = "SlimTrade";
    private final String folderOther = ".slimtrade";
    private final String imageFolder = "images";
    private final String logsFolder = "logs";
    private final String fileName = "settings.json";
    private final String stashFileName = "stash.json";
    private final String pinSaveFileName = "pins.json";
    private final String patcherFileName = "patcher.json";
    private final String overlayFileName = "overlay.json";
    private final String scannerFileName = "scanner.json";

    private boolean validSavePath = false;

    // File Stuff
    private FileReader fr;
    private BufferedReader br;
    private FileWriter fw;
    private Gson gson;

    // TODO : OPTIMIZE :    Combine all saving and loading functions into one using wildcars?
    // TODO :               Also need to add file.exists() check to avoid try/catch

    public SaveManager() {

        // Set save directory
        String os = (System.getProperty("os.name")).toUpperCase();
        if (os.contains("WIN")) {
            INSTALL_DIRECTORY = System.getenv("LocalAppData") + File.separator + folderWin;
        } else {
            INSTALL_DIRECTORY = System.getProperty("user.home") + File.separator + folderOther;
        }
        savePath = INSTALL_DIRECTORY + File.separator + fileName;
        stashSavePath = INSTALL_DIRECTORY + File.separator + stashFileName;
        pinSavePath = INSTALL_DIRECTORY + File.separator + pinSaveFileName;
        patcherSavePath = INSTALL_DIRECTORY + File.separator + patcherFileName;
        overlaySavePath = INSTALL_DIRECTORY + File.separator + overlayFileName;
        scannerSavePath = INSTALL_DIRECTORY + File.separator + scannerFileName;
        File saveDir = new File(INSTALL_DIRECTORY);
        File imageDir = new File(INSTALL_DIRECTORY + File.separator + imageFolder);
        File logsDir = new File(INSTALL_DIRECTORY + File.separator + logsFolder);
        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }
        if (!imageDir.exists()) {
            imageDir.mkdirs();
        }
        if (!logsDir.exists()) {
            logsDir.mkdirs();
        }
        if (saveDir.exists()) {
            validSavePath = true;
        }
        gson = new Gson();
    }

    public String getImageFolder() {
        return INSTALL_DIRECTORY + File.separator + imageFolder;
    }

    public void loadSettingsFromDisk() {
        boolean err = false;
        try {
            settingsSaveFile = gson.fromJson(getJsonString(savePath), SettingsSaveFile.class);
        } catch (JsonSyntaxException e) {
            err = true;
        }
        if (settingsSaveFile == null || err) {
            settingsSaveFile = new SettingsSaveFile();
        }
    }

    public void loadStashFromDisk() {
        boolean err = false;
        try {
            stashSaveFile = gson.fromJson(getJsonString(stashSavePath), StashSaveFile.class);
        } catch (JsonSyntaxException e) {
            err = true;
        }
        if (stashSaveFile == null || err) {
            stashSaveFile = new StashSaveFile();
        }
    }

    public void loadOverlayFromDisk() {
        boolean err = false;
        try {
            overlaySaveFile = gson.fromJson(getJsonString(overlaySavePath), OverlaySaveFile.class);
        } catch (JsonSyntaxException e) {
            err = true;
        }
        if (overlaySaveFile == null || err) {
            overlaySaveFile = new OverlaySaveFile();
        }
    }

    public void loadScannerFromDisk() {
        boolean err = false;
        try {
            scannerSaveFile = gson.fromJson(getJsonString(scannerSavePath), ScannerSaveFile.class);
        } catch (JsonSyntaxException e) {
            err = true;
        }
        if (scannerSaveFile == null || err) {
            scannerSaveFile = new ScannerSaveFile();
        }
    }

    public void loadPinsFromDisk() {
        boolean err = false;
        try {
            pinSaveFile = gson.fromJson(getJsonString(pinSavePath), PinSaveFile.class);
        } catch (JsonSyntaxException e) {
            err = true;
        }
        if (pinSaveFile == null || err) {
            pinSaveFile = new PinSaveFile();
        }
    }

    private String getJsonString(String path) {
        StringBuilder builder = new StringBuilder();
        try {
            br = new BufferedReader(new FileReader(path));
            while (br.ready()) {
                builder.append(br.readLine());
            }
            br.close();
            return builder.toString();
        } catch (JsonSyntaxException | IOException e) {
            return null;
        }
    }

    public void saveSettingsToDisk() {
        try {
            fw = new FileWriter(savePath);
            fw.write(gson.toJson(settingsSaveFile));
            fw.close();
        } catch (IOException e) {
            return;
        }
    }

    public void saveStashToDisk() {
        try {
            fw = new FileWriter(stashSavePath);
            fw.write(gson.toJson(stashSaveFile));
            fw.close();
        } catch (IOException e) {
            return;
        }
    }

    public void saveOverlayToDisk() {
        try {
            fw = new FileWriter(overlaySavePath);
            fw.write(gson.toJson(overlaySaveFile));
            fw.close();
        } catch (IOException e) {
            return;
        }
    }

    public void saveScannerToDisk() {
        try {
            fw = new FileWriter(scannerSavePath);
            fw.write(gson.toJson(scannerSaveFile));
            fw.close();
        } catch (IOException e) {
            return;
        }
    }

    public void savePinsToDisk() {
        try {
            fw = new FileWriter(pinSavePath);
            fw.write(gson.toJson(pinSaveFile));
            fw.close();
        } catch (IOException e) {
            return;
        }
    }

    public int validateClientPath() {
        int clientCount = 0;
        String clientPath = settingsSaveFile.clientPath;
        if (clientPath != null) {
            File file = new File(clientPath);
            if (file.exists() && file.isFile()) {
                return 1;
            }
        }
        String[] commonDrives = {"C", "D", "E", "F"};
        ArrayList<String> stubs = new ArrayList<>();
        stubs.add(":/Program Files/Steam/steamapps/common/Path of Exile/logs/Client.txt");
        stubs.add(":/Program Files (x86)/Steam/steamapps/common/Path of Exile/logs/Client.txt");
        stubs.add(":/Program Files/Grinding Gear Games/Path of Exile/logs/Client.txt");
        stubs.add(":/Program Files (x86)/Grinding Gear Games/Path of Exile/logs/Client.txt");
        stubs.add(":/Steam/steamapps/common/Path of Exile/logs/Client.txt");
        stubs.add(":/SteamLibrary/steamapps/common/Path of Exile/logs/Client.txt");
        clientPaths.clear();
        for (String drive : commonDrives) {
            for (String stub : stubs) {
                File clientFile = new File(drive + stub);
                if (clientFile.exists() && clientFile.isFile()) {
//                    System.out.println("Found : " + drive + stub);
                    clientPaths.add(drive + stub);
                    clientCount++;
                }
            }
        }
        if (clientCount == 1) {
            settingsSaveFile.clientPath = clientPaths.get(0);
        }
        return clientCount;
    }

    public static void recursiveSave(Component component) {
        if (component instanceof ISaveable) {
            ((ISaveable) component).save();
        }
        if (component instanceof Container) {
            for (Component c : ((Container) component).getComponents()) {
                recursiveSave(c);
            }
        }
    }

    public static void recursiveLoad(Component component) {
        if (component instanceof ISaveable) {
            ((ISaveable) component).load();
        }
        if (component instanceof Container) {
            for (Component c : ((Container) component).getComponents()) {
                recursiveLoad(c);
            }
        }
    }

}
