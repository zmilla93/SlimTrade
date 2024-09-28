package com.slimtrade.core.utility;

import com.google.gson.Gson;
import com.slimtrade.core.enums.PathOfExileLeague;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.ninja.NinjaEndpoint;
import com.slimtrade.core.ninja.NinjaOverview;
import com.slimtrade.core.ninja.NinjaResponse;
import com.slimtrade.core.ninja.NinjaSyncListener;
import com.slimtrade.core.ninja.responses.INinjaEntry;
import com.slimtrade.modules.updater.ZLogger;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * This class handles interfacing with poe.ninja asynchronously.
 * <p>
 * 1. Add a NinjaSyncListener, the onSync callback will be called anytime new data is fetched.
 * <p>
 * 2. Call sync(Endpoints...) anytime you want to ensure data is fresh. Safe to call this frequently,
 * as it will only fetch data when current data is stale.
 */
public class NinjaInterface {

    private static final HashMap<String, INinjaEntry> dataCache = new HashMap<>();
    private static final HashMap<NinjaEndpoint, Long> endpointTimerCache = new HashMap<>();
    private static final HashMap<NinjaEndpoint, ArrayList<NinjaSyncListener>> listenerMap = new HashMap<>();
    private static final Executor executor = Executors.newSingleThreadExecutor();
    public static final boolean useLocalDatasets = true;

    static {
        for (NinjaEndpoint endpoint : NinjaEndpoint.values())
            listenerMap.put(endpoint, new ArrayList<>());
        if (useLocalDatasets) {
            loadLocalDatasets();
        }
    }

    public static void sync(NinjaEndpoint... endpoints) {
        if (useLocalDatasets) return;
        for (NinjaEndpoint endpoint : endpoints) {
            if (isCached(endpoint)) continue;
            // FIXME: Temp league
            fetchData(endpoint, PathOfExileLeague.TEMP);
        }
    }

    public static void addSyncListener(NinjaSyncListener listener, NinjaEndpoint... endpoints) {
        for (NinjaEndpoint endpoint : endpoints) {
            ArrayList<NinjaSyncListener> listeners = listenerMap.get(endpoint);
            listeners.add(listener);
        }
    }

    public static void removeSyncListener(NinjaSyncListener listener, NinjaEndpoint... endpoints) {
        for (NinjaEndpoint endpoint : endpoints) {
            ArrayList<NinjaSyncListener> listeners = listenerMap.get(endpoint);
            listeners.remove(listener);
        }
    }

    private static void fetchData(NinjaEndpoint endpoint, PathOfExileLeague league) {
        executor.execute(() -> {
            String url = endpoint.getURL(league);
            ZLogger.log("Fetching " + endpoint.toString().toLowerCase() + " data from poe.ninja.");
            String value = HttpRequester.getPageContents(url);
            INinjaEntry[] data = null;
            if (endpoint.overview == NinjaOverview.ITEM) {
                data = new Gson().fromJson(value, NinjaResponse.Simple.class).lines;
            } else if (endpoint.overview == NinjaOverview.CURRENCY) {
                data = new Gson().fromJson(value, NinjaResponse.Fragment.class).lines;
            }
            cacheData(endpoint, data);
            ArrayList<NinjaSyncListener> listeners = listenerMap.get(endpoint);
            SwingUtilities.invokeLater(() -> {
                for (NinjaSyncListener listener : listeners) listener.onSync();
            });

        });
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
        return System.currentTimeMillis() < expirationTime;
    }

    private static void markCache(NinjaEndpoint endpoint) {
        endpointTimerCache.put(endpoint, System.currentTimeMillis());
    }

    public static void loadLocalDatasets() {
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
    public static INinjaEntry getEntry(String name) {
        return dataCache.get(name);
    }

}
