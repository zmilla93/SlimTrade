package com.slimtrade.core.utility;

import com.google.gson.Gson;
import com.slimtrade.core.ninja.NinjaResponse;
import com.slimtrade.core.ninja.responses.NinjaFragmentEntry;
import com.slimtrade.core.ninja.responses.NinjaScarabEntry;

import java.util.HashMap;

/**
 * This class handles interfacing with poe.ninja.
 */
public class NinjaInterface {

    private static final HashMap<String, NinjaScarabEntry> scarabData = new HashMap<>();
    private static final HashMap<String, NinjaFragmentEntry> fragmentData = new HashMap<>();

    public static void sync() {
        // TODO: Use actual poe.ninja api.
        String json = ZUtil.getFileAsString("C:\\Docs\\ninja-scarabs.json", false);
        String fragmentJson = ZUtil.getFileAsString("C:\\Docs\\ninja-fragments.json", false);
        if (json == null) return;
        Gson gson = new Gson();
        NinjaResponse.Scarab data = gson.fromJson(json, NinjaResponse.Scarab.class);
        NinjaResponse.Fragment fragmentResponse = gson.fromJson(fragmentJson, NinjaResponse.Fragment.class);
        for (NinjaScarabEntry entry : data.lines) {
            scarabData.put(entry.name, entry);
        }
        for (NinjaFragmentEntry entry : fragmentResponse.lines) {
            fragmentData.put(entry.currencyTypeName, entry);
        }
    }

    public static NinjaScarabEntry getScarab(String name) {
        if (scarabData.containsKey(name)) return scarabData.get(name);
        return null;
    }

    public static NinjaFragmentEntry getFragment(String name) {
        if (fragmentData.containsKey(name)) return fragmentData.get(name);
        return null;
    }

}
