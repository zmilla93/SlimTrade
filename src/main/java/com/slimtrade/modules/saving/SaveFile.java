package com.slimtrade.modules.saving;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.slimtrade.modules.listening.ListenManager;

import java.awt.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Allows easy linking of a data class, a json save file, and a list of ISavable components.
 */
public class SaveFile<T> extends ListenManager<ISaveListener> {

    public T data;
    public final String path;
    public final Class<T> classType;
    private final ArrayList<ISavable> savables = new ArrayList<>();
    //    private final ArrayList<ISaveListener> saveListeners = new ArrayList<>();
    private final Timer autoSaveTimer = new Timer();
    private TimerTask saveTask;
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public SaveFile(String path, Class<T> classType) {
        this.path = path;
        this.classType = classType;
//        loadFromDisk();
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

    /**
     * Saves the data class to a json file.
     */
    public synchronized void saveToDisk() {
        try {
            for (ISavable c : savables) {
                c.save();
            }
            File file = new File(path);
            Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
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
        File file = new File(path);
        if (file.exists()) {
            try {
                data = gson.fromJson(getFileAsString(file.getPath()), classType);
                if (data != null) {
                    for (ISaveListener listener : listeners) {
                        listener.onLoad();
                    }
                    return;
                }
            } catch (JsonSyntaxException e) {
                return;
            }
        }
        try {
            data = classType.getDeclaredConstructor().newInstance();
            for (ISaveListener listener : listeners) {
                listener.onLoad();
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * Resets a timer everytime this function is called.
     * When the timer expires, the file is saves.
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

//    public void addListener(ISaveListener listener) {
//        saveListeners.add(listener);
//    }
//
//    public void removeListener(ISaveListener listener) {
//        saveListeners.remove(listener);
//    }
//
//    public void removeAllListeners() {
//        saveListeners.clear();
//    }

    // FIXME : This can be cleaned up
    private static String getFileAsString(String path) {
        StringBuilder builder = new StringBuilder();
        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
            while (br.ready()) {
                builder.append(br.readLine());
            }
            br.close();
            return builder.toString();
        } catch (IOException e) {
            return null;
        }
    }

}
