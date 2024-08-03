package com.slimtrade.modules.saving;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.slimtrade.core.saving.savefiles.AbstractSaveFile;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.modules.listening.ListenManager;
import com.slimtrade.modules.updater.ZLogger;

import java.awt.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Allows easy linking of a data class, a json save file, and a list of ISavable components.
 */
public class SaveFile<T extends AbstractSaveFile> extends ListenManager<ISaveListener> {

    public T data;
    public final String path;
    public final boolean isPathRelative;
    public final Class<T> classType;
    private boolean loadedExistingData = false;
    private final ArrayList<ISavable> savables = new ArrayList<>();
    private final Timer autoSaveTimer = new Timer();
    private TimerTask saveTask;
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public SaveFile(String path, Class<T> classType) {
       this(path, classType, false);
    }

    public SaveFile(String path, Class<T> classType, boolean isPathRelative) {
        this.path = path;
        this.classType = classType;
        this.isPathRelative = isPathRelative;
    }

    public boolean loadedExistingData() {
        return loadedExistingData;
    }

    public boolean fileExists() {
        File file = new File(path);
        return file.exists() && file.isFile();
    }

    /**
     * Register a single ISavable component.
     * Only user if really needed. Try using registerSavableContainer instead.
     *
     * @param savable
     */
    public void registerSavable(ISavable savable) {
        savables.add(savable);
    }

    /**
     * Recursively registers all ISavable components in a parent component.
     *
     * @param component
     */
    public void registerSavableContainer(Component component) {
        if (component instanceof ISavable) {
            savables.add((ISavable) component);
        }
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                registerSavableContainer(child);
            }
        }
    }

    /**
     * Calls load() on all registered ISavable objects.
     */
    public synchronized void revertChanges() {
        for (ISavable savable : savables) {
            savable.load();
        }
    }

    public synchronized void saveToDisk() {
        saveToDisk(true);
    }

    /**
     * Saves the data class to a json file.
     */
    public synchronized void saveToDisk(boolean procSavables) {
        try {
            if (procSavables) {
                for (ISavable c : savables) {
                    c.save();
                }
            }
            File file = new File(path);
            Writer writer = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(file.toPath()), StandardCharsets.UTF_8));
            writer.write(gson.toJson(data));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (ISaveListener listener : listeners) {
            listener.onSave();
        }
    }

    /**
     * Reads a saved json file from disk and converts it to an equivalent data class.
     * If no file is found, creates a new instance of the class.
     * Automatically called when SaveFile is created.
     */
    public synchronized void loadFromDisk() {
        if (ZUtil.fileExists(path, isPathRelative)) {
            try {
                data = gson.fromJson(getFileAsString(path), classType);
                if (data != null) {
                    loadedExistingData = true;
                    return;
                }
            } catch (JsonSyntaxException ignore) {
                ZLogger.err("Save file '" + path + "' is corrupted, creating new file.");
            }
        } else {
            ZLogger.log("Save file '" + path + "' doesn't exist, creating new file.");
        }
        initData();
    }

    /**
     * This discards all old save information and generates a new one.
     * Should only be used if file is not found or is corrupted.
     */
    public void initData() {
        try {
            data = classType.getDeclaredConstructor().newInstance();
            data.saveFileVersion = data.getCurrentTargetVersion();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Resets a timer everytime this function is called.
     * When the timer expires, the file is saved.
     */
    public void triggerSaveAfterIdle() {
        if (saveTask != null) {
            saveTask.cancel();
            autoSaveTimer.purge();
        }
        saveTask = new TimerTask() {
            @Override
            public void run() {
                saveToDisk();
            }
        };
        autoSaveTimer.schedule(saveTask, 1500);
    }

    private static String getFileAsString(String path) {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = ZUtil.getBufferedReader(path);
        try {
            while (reader.ready()) {
                builder.append(reader.readLine());
            }
            reader.close();
            return builder.toString();
        } catch (IOException e) {
            return null;
        }
    }

}
