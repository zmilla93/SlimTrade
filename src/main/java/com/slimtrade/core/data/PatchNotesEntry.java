package com.slimtrade.core.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class PatchNotesEntry {

    // FIXME : Switch this to an actual version class when that is finished
    public String version;
    public String text;
    public boolean valid;

    public PatchNotesEntry(String version, String text) {
        this.version = version;
        this.text = text;
        // FIXME : Add proper validation
        valid = true;
    }

    @Override
    public String toString() {
        return version;
    }

    public static ArrayList<PatchNotesEntry> readLocalPatchNotes(String path) {
        ArrayList<PatchNotesEntry> entries = new ArrayList<>();
        File file = new File(path);
        if (file.exists()) {
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(path));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            JsonArray json = JsonParser.parseReader(reader).getAsJsonArray();
            for (int i = 0; i < json.size(); i++) {
                JsonObject obj = json.get(i).getAsJsonObject();
                String tag = obj.get("tag_name").getAsString();
                String body = obj.get("body").getAsString();
                PatchNotesEntry entry = new PatchNotesEntry(tag, body);
                if (entry.valid) entries.add(entry);
            }
        }
        return entries;
    }

    // FIXME : This is the same function as used in SaveFile, should move to a utility file
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
