package com.slimtrade.core.saving;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.slimtrade.core.saving.legacy.ScannerPatcher0to1;
import com.slimtrade.core.saving.legacy.SettingsSaveFilePatcher0to1;
import com.slimtrade.core.saving.legacy.StashSavePatcher0to1;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
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
        handlePatch(new SettingsSaveFilePatcher0to1());
        handlePatch(new StashSavePatcher0to1());
        handlePatch(new ScannerPatcher0to1());
    }

    private static boolean handlePatch(ISavePatcher patcher) {
        boolean patched = false;
        if (patcher.requiresPatch()) patched = patcher.patch();
        if (patched) patcher.applyNewVersion();
        System.out.println("Patch [" + patcher.getClass().getSimpleName() + "]: " + patched);
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
        System.out.println("=== Comparison Report for [" + from.getClass().getSimpleName() + "] & [" + to.getClass().getSimpleName() + "] ===");
        if (invalidTypes.size() > 0) System.out.println("=== INVALID TYPES ===");
        else System.out.println("No invalid type comparisons found!");
        for (String s : invalidTypes) System.out.println("\t" + s);
        if (invalidTypes.size() > 0) System.out.println();

        if (nonMatchingFields.size() > 0) System.out.println("=== NON MATCHING FIELDS ===");
        else System.out.println("All inspected field names match!");
        for (String s : nonMatchingFields) System.out.println("\t" + s);
        if (nonMatchingFields.size() > 0) System.out.println();
    }

    public static void debugSaveToDisk(String path, Object data) {
        try {
            File file = new File(path);
            Writer writer = new OutputStreamWriter(Files.newOutputStream(file.toPath()), StandardCharsets.UTF_8);
            writer.write(gson.toJson(data));
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
