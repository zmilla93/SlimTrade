package com.slimtrade.core.utility;

import com.google.gson.Gson;
import com.slimtrade.core.ninja.NinjaResponse;
import com.slimtrade.core.ninja.responses.NinjaFragmentEntry;
import com.slimtrade.core.ninja.responses.NinjaScarabEntry;
import com.slimtrade.core.ninja.responses.NinjaSimpleEntry;

import java.util.HashMap;

/**
 * This class handles interfacing with poe.ninja.
 */
public class NinjaInterface {

    private static final HashMap<String, Object> dataMap = new HashMap<>();

    public static void sync() {
        // TODO: Use actual poe.ninja api.
        Gson gson = new Gson();
        String scarabJson = ZUtil.getFileAsString("C:\\Docs\\ninja-scarabs.json", false);
        String fragmentJson = ZUtil.getFileAsString("C:\\Docs\\ninja-fragments.json", false);
        String essenceJson = ZUtil.getFileAsString("C:\\Docs\\ninja-essence.json", false);
        NinjaResponse.Scarab data = gson.fromJson(scarabJson, NinjaResponse.Scarab.class);
        NinjaResponse.Fragment fragmentResponse = gson.fromJson(fragmentJson, NinjaResponse.Fragment.class);
        NinjaResponse.Simple essenceResponse = gson.fromJson(essenceJson, NinjaResponse.Simple.class);
        for (NinjaScarabEntry entry : data.lines) dataMap.put(entry.name, entry);
        for (NinjaFragmentEntry entry : fragmentResponse.lines) dataMap.put(entry.currencyTypeName, entry);
        for (NinjaSimpleEntry entry : essenceResponse.lines) dataMap.put(entry.name, entry);
    }

    public static String getText(String name) {
        Object obj = dataMap.get(name);
        if (obj == null) return null;
        return obj.toString();
    }

}
