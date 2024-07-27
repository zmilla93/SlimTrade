package com.slimtrade.core.saving.legacy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.slimtrade.core.saving.legacy.patcher.*;
import com.slimtrade.modules.updater.ZLogger;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;

public class SaveFilePatcherManager {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static final boolean DEBUG_REPORT = false;

    public static void handleSaveFilePatching() {
        handlePatch(new PatcherSettings0to1());
        handlePatch(new PatcherSettings1to2());
        handlePatch(new PatcherSettings2to3());
        handlePatch(new PatcherStash0to1());
        handlePatch(new PatcherScanner0to1());
        handlePatch(new PatcherScanner1to2());
        handlePatch(new PatcherOverlay0to1());
    }

    private static boolean handlePatch(ISavePatcher patcher) {
        String patcherName = patcher.getClass().getSimpleName();
        boolean patched = false;
        try {
            if (patcher.requiresPatch()) {
                ZLogger.log("Patch required: [" + patcherName + "]");
                patched = patcher.patch();
                String errorMessage = patcher.getErrorMessage();
                if (errorMessage != null) ZLogger.err(errorMessage);
                if (patched) ZLogger.log("Patch successful!");
                else ZLogger.err("Patch failed!");
            }
            if (patched) patcher.applyNewVersion();
        } catch (Exception e) {
            ZLogger.err("[" + patcherName + "] Encountered corrupted legacy save file, recreating data.");
            e.printStackTrace();
            patcher.handleCorruptedFile();
        }
        return patched;
    }

    /**
     * Uses reflection to copy fields from one save file to another.
     * Serialized field name and field type must match.
     *
     * @param from Old save file
     * @param to   New save file
     */
    public static void copyMatchingFields(Object from, Object to) {
        Field[] fieldsFrom = from.getClass().getFields();
        Field[] fieldsTo = to.getClass().getFields();
        HashMap<String, Field> map = new HashMap<>();
        for (Field fieldTo : fieldsTo) {
            map.put(fieldTo.getName(), fieldTo);
        }
        ArrayList<String> invalidTypes = new ArrayList<>();
        ArrayList<String> noMatch = new ArrayList<>();

        for (Field fieldFrom : fieldsFrom) {
            if (Modifier.isTransient(fieldFrom.getModifiers())) continue;
            if (Modifier.isStatic(fieldFrom.getModifiers())) continue;
            if (map.containsKey(fieldFrom.getName())) {
                Field fieldTo = map.get(fieldFrom.getName());
                if (fieldFrom.getType().equals(fieldTo.getType())) {
                    // Fields match, copy data
                    try {
                        Object value = fieldFrom.get(from);
                        fieldTo.set(to, value);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    // Field names match, but types don't
                    invalidTypes.add(fieldFrom.getName());
                }
            } else {
                // No matching field was found
                noMatch.add(fieldFrom.getName());
            }
        }
        debugReport(from, to, invalidTypes, noMatch);
    }

    private static void debugReport(Object from, Object to, ArrayList<String> invalidTypes, ArrayList<String> nonMatchingFields) {
        if (!DEBUG_REPORT) return;
        ZLogger.log("=== Comparison Report for [" + from.getClass().getSimpleName() + "] & [" + to.getClass().getSimpleName() + "] ===");
        if (invalidTypes.size() > 0) ZLogger.log("=== INVALID TYPES ===");
        else ZLogger.log("No invalid type comparisons found!");
        for (String s : invalidTypes) ZLogger.log("\t" + s);
        if (invalidTypes.size() > 0) ZLogger.log("");

        if (nonMatchingFields.size() > 0) ZLogger.log("=== NON MATCHING FIELDS ===");
        else ZLogger.log("All inspected field names match!");
        for (String s : nonMatchingFields) ZLogger.log("\t" + s);
        if (nonMatchingFields.size() > 0) ZLogger.log("");
    }

    public static void debugSaveToDisk(String path, Object data) {
        try {
            File file = new File(path);
            Writer writer = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(file.toPath()), StandardCharsets.UTF_8));
            writer.write(gson.toJson(data));
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
