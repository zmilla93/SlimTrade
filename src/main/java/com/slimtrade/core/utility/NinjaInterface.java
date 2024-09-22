package com.slimtrade.core.utility;

import com.google.gson.Gson;
import com.slimtrade.core.enums.PathOfExileLeague;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.ninja.NinjaEndpoint;
import com.slimtrade.core.ninja.NinjaOverview;
import com.slimtrade.core.ninja.NinjaResponse;
import com.slimtrade.core.ninja.responses.INinjaEntry;
import com.slimtrade.modules.updater.ZLogger;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * This class handles interfacing with poe.ninja.
 */
public class NinjaInterface {

    private static final HashMap<String, INinjaEntry> dataCache = new HashMap<>();
    private static final HashMap<NinjaEndpoint, Long> endpointTimerCache = new HashMap<>();

    public static void sync() {
        // TODO: Use actual poe.ninja api.
        loadLocalDatasets();
//        sync(NinjaEndpoint.CURRENCY);
//        for (NinjaEndpoint endpoint : NinjaEndpoint.values()) sync(endpoint);
    }

    public static void sync(NinjaEndpoint endpoint) {
        if (isCached(endpoint)) return;
        // FIXME: Temp league
        INinjaEntry[] data = fetchData(endpoint, PathOfExileLeague.TEMP);
        cacheData(endpoint, data);
    }

    private static INinjaEntry[] fetchData(NinjaEndpoint endpoint, PathOfExileLeague league) {
        String url = endpoint.getURL(league);
        ZLogger.log("Fetching " + endpoint.toString().toLowerCase() + " data from poe.ninja.");
        String value = HttpRequester.getPageContents(url);
        INinjaEntry[] data = null;
        if (endpoint.overview == NinjaOverview.ITEM) {
            data = new Gson().fromJson(value, NinjaResponse.Simple.class).lines;
        } else if (endpoint.overview == NinjaOverview.CURRENCY) {
            data = new Gson().fromJson(value, NinjaResponse.Fragment.class).lines;
        }
        return data;
    }

    private static void cacheData(NinjaEndpoint endpoint, INinjaEntry[] data) {
        markCache(endpoint);
        if (data == null) {
            ZLogger.err("Poe.ninja returned null data!");
            return;
        }
        unloadDataset(data);
        loadDataset(data);
    }

    private static boolean isCached(NinjaEndpoint endpoint) {
        if (!endpointTimerCache.containsKey(endpoint)) return false;
        long cacheTime = endpointTimerCache.get(endpoint);
        // FIXME : Make this configurable?
        long cacheDuration = TimeUnit.MINUTES.toMillis(60);
        long expirationTime = cacheTime + cacheDuration;
        return System.currentTimeMillis() > expirationTime;
    }

    private static void markCache(NinjaEndpoint endpoint) {
        endpointTimerCache.put(endpoint, System.currentTimeMillis());
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
        for (INinjaEntry entry : data) dataCache.put(entry.getName(), entry);
    }

    private static void unloadDataset(INinjaEntry[] data) {
        for (INinjaEntry entry : data) dataCache.remove(entry.getName());
    }

    private static <T> T getDatasetFromFile(String fileName, Class<T> classType) {
        PathOfExileLeague league = PathOfExileLeague.TEMP;
        String text = ZUtil.getFileAsString(SaveManager.getNinjaDirectory() + league + File.separator + fileName + ".json", false);
        return new Gson().fromJson(text, classType);
    }

    /**
     * When given the name of an item tracked by poe.ninja, ie "Harvest Scarab",
     * returns a formatted string with the price, ie "22c", or null if no price is found.
     * <p>
     * When using this function, be sure to sync the required endpoints and register a callback
     * to correctly update to UI when the cache expires.
     */
    public static @Nullable String getItemPriceText(String name) {
        Object obj = dataCache.get(name);
        if (obj == null) return null;
        return obj.toString();
    }

}
