package com.slimtrade.core.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.slimtrade.core.saving.BasicSavableComponent;
import com.slimtrade.core.saving.ISavable;
import com.slimtrade.core.saving.SavableComponent;
import com.slimtrade.core.saving.SettingsSaveFile;
import com.slimtrade.gui.managers.FrameManager;

import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class SaveManager {

    // Save Directory
    private static String saveDirectory;
    private static final String folderWin = "SlimTrade-Rebuild";
    private static final String folderOther = ".slimtrade-rebuild";

    // Save Files
    private final String settingsSaveName = "settings.json";
    public SettingsSaveFile settingsSaveFile;

    // Subfolder Names
    private static final String audioFolderName = "audio";
    private static final String imagesFolderName = "images";

    // Parsing
    private final Gson gson;
    private final ArrayList<SavableComponent> savableComponents = new ArrayList<>();
    private final ArrayList<ISavable> savables = new ArrayList<>();

    public SaveManager() {
//        gson = new Gson();
        gson = new GsonBuilder().setPrettyPrinting().create();
        getSaveDirectory();
        loadSaveSettings();
        File file = new File(saveDirectory);
        if (!file.exists()) {
            boolean success = file.mkdirs();
            if (!success) {
                // TODO : log error
                System.out.println("ERRRR");
            }
        }
    }

    public void registerSavable(ISavable savable) {
        savables.add(savable);
    }

    public void registerSaveElement(JComponent component, String fieldName, Object saveFileClass) {
        savableComponents.add(new BasicSavableComponent(component, fieldName, saveFileClass));
    }

    public void saveToFile() {
        try {
            for (SavableComponent c : savableComponents) {
                c.save();
            }
            for (ISavable c : savables) {
                c.save();
            }
            File file = new File(saveDirectory + settingsSaveName);
            Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
            writer.write(gson.toJson(settingsSaveFile));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void revertChanges() {
        for (SavableComponent c : savableComponents) {
            c.load();
        }
        for (ISavable c : savables) {
            c.load();
        }
        FrameManager.optionsWindow.revalidate();
    }


    public static String getAudioDirectory() {
        return getSaveDirectory() + audioFolderName + File.separator;
    }

    public static String getSaveDirectory() {
        if (saveDirectory == null) {
            String os = (System.getProperty("os.name")).toUpperCase();
            if (os.contains("WIN")) {
                saveDirectory = System.getenv("LocalAppData") + File.separator + folderWin + File.separator;
            } else {
                saveDirectory = System.getProperty("user.home") + File.separator + folderOther + File.separator;
            }
        }
        return saveDirectory;
    }

    private void loadSaveSettings() {
        File file = new File(getSaveDirectory() + settingsSaveName);
        if (file.exists()) {
            settingsSaveFile = gson.fromJson(getJsonString(file.getPath()), SettingsSaveFile.class);
        } else {
            settingsSaveFile = new SettingsSaveFile();
        }
    }

    private String getJsonString(String path) {
        StringBuilder builder = new StringBuilder();
        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
            while (br.ready()) {
                builder.append(br.readLine());
            }
            br.close();
            return builder.toString();
        } catch (JsonSyntaxException | IOException e) {
            return null;
        }
    }
}