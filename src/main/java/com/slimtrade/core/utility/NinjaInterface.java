package com.slimtrade.core.utility;

import com.google.gson.Gson;
import com.slimtrade.core.enums.PathOfExileLeague;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.ninja.NinjaResponse;
import com.slimtrade.core.ninja.responses.INinjaEntry;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.HashMap;

/**
 * This class handles interfacing with poe.ninja.
 */
public class NinjaInterface {

    private static final HashMap<String, Object> dataMap = new HashMap<>();

    public static void sync() {
        // TODO: Use actual poe.ninja api.
        loadLocalDatasets();
    }

    private static void loadLocalDatasets() {
        loadDataset(getDatasetFromFile("Currency", NinjaResponse.Fragment.class).lines);
        loadDataset(getDatasetFromFile("DeliriumOrb", NinjaResponse.Simple.class).lines);
        loadDataset(getDatasetFromFile("Essence", NinjaResponse.Simple.class).lines);
        loadDataset(getDatasetFromFile("Fossil", NinjaResponse.Simple.class).lines);
        loadDataset(getDatasetFromFile("Fragment", NinjaResponse.Fragment.class).lines);
        loadDataset(getDatasetFromFile("Oil", NinjaResponse.Simple.class).lines);
        loadDataset(getDatasetFromFile("Scarab", NinjaResponse.Simple.class).lines);
    }

    private static void loadDataset(INinjaEntry[] data) {
        for (INinjaEntry entry : data) dataMap.put(entry.getName(), entry);
    }

    private static void unloadDataset(INinjaEntry[] data) {
        for (INinjaEntry entry : data) dataMap.remove(entry.getName());
    }

    private static <T> T getDatasetFromFile(String fileName, Class<T> classType) {
        PathOfExileLeague league = PathOfExileLeague.TEMP;
        String text = ZUtil.getFileAsString(SaveManager.getNinjaDirectory() + league + File.separator + fileName + ".json", false);
        return new Gson().fromJson(text, classType);
    }

    public static @Nullable String getText(String name) {
        Object obj = dataMap.get(name);
        if (obj == null) return null;
        return obj.toString();
    }

}
