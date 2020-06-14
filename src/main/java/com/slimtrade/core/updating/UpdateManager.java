package com.slimtrade.core.updating;

import com.slimtrade.App;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class UpdateManager {

    private ArrayList<ReleaseData> releases = new ArrayList<>();

    File launcherJSONFile;
    JSONObject launcherJSON;
    boolean autoUpdate;
    String currentVersion;

    public boolean foundReleaseData = false;

    public UpdateManager() {
        launcherJSONFile = new File(App.saveManager.installDirectory, "launcher.json");
        boolean existingJSON = false;
        if (launcherJSONFile.exists()) {
            try {
                FileReader fr = new FileReader(launcherJSONFile);
                BufferedReader br = new BufferedReader(fr);
                StringBuilder builder = new StringBuilder();
                while (br.ready()) {
                    builder.append(br.readLine());
                }
                br.close();
                fr.close();
                launcherJSON = new JSONObject(builder.toString());
                autoUpdate = launcherJSON.getBoolean("autoUpdate");
                currentVersion = launcherJSON.getString("versionTag");
                existingJSON = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Initialize JSON if needed
        if (!existingJSON) {
            launcherJSON = new JSONObject();
            autoUpdate = true;
            currentVersion = "";
            launcherJSON.put("autoUpdate", true);
            launcherJSON.put("versionTag", currentVersion);
            try {
                FileWriter fw = new FileWriter(launcherJSONFile);
                fw.write(launcherJSON.toString());
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isAutoUpdate() {
        return autoUpdate;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public ArrayList<ReleaseData> getReleaseData() {
        return releases;
    }

    public boolean fetchReleaseData() {
//        String version;
        releases = new ArrayList<>();
        BufferedReader br = null;
        InputStream is = null;
        try {
            is = new URL("https://api.github.com/repos/zmilla93/slimtrade/releases").openStream();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        if (is == null) {
            // TODO : This will catch rate limit
//            App.debugger.log("NULL::::");
            return false;
        }
        try {
            br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();
            try {
                while (br.ready()) {
                    builder.append(br.readLine());
                }
            } catch (IOException e) {
                App.debugger.log("Error while fetching latest version : " + e.getMessage());
                return false;
            }
            System.out.println("STR : " + builder.toString());
            JSONArray json = new JSONArray(builder.toString());
            for (Object o : json) {
                if (o instanceof JSONObject) {
                    JSONObject obj = (JSONObject) o;
                    releases.add(new ReleaseData(obj));
                }
            }
            foundReleaseData = true;
            return true;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
//                    App.debugger.log(e.getStackTrace());
                }
            }
        }
    }

}
