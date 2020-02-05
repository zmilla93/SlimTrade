package com.slimtrade.core.managers;

import com.google.gson.*;
import com.slimtrade.App;
import com.slimtrade.core.SaveSystem.SaveFile;

import java.io.*;
import java.lang.reflect.Type;
import java.time.*;

public class SaveManager {

    // Public Info
    public final String savePath;
    public final String saveDirectory;
    public SaveFile saveFile = new SaveFile();

    //Internal
    private final String folderWin = "SlimTrade";
    private final String folderOther = ".slimtrade";
    private final String fileName = "settings.json";
    private final String os = (System.getProperty("os.name")).toUpperCase();
    private boolean validSavePath = false;

    // File Stuff
    private FileReader fr;
    private BufferedReader br;
    private FileWriter fw;
    private Gson gson;

    public SaveManager(){

        // Set save directory

        if (os.contains("WIN")) {
            saveDirectory = System.getenv("LocalAppData") + File.separator + folderWin;
        } else {
            saveDirectory = System.getProperty("user.home") + File.separator + folderOther;
        }
        savePath = saveDirectory + File.separator + fileName;
        File saveDir = new File(saveDirectory);
        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }
        if(saveDir.exists()){
            validSavePath = true;
        }

        // Gson instance with added support for LocalDateTime
        gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                Instant instant = Instant.ofEpochMilli(json.getAsJsonPrimitive().getAsLong());
//                return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                return ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toLocalDateTime();
            }
        }).create();

        System.out.println("Save Directory : " + saveDirectory);
        System.out.println("Save path : " + savePath);
        loadFromDisk();
        saveToDisk();
    }

    public void loadFromDisk() {
        StringBuilder builder = new StringBuilder();
        try {
            br = new BufferedReader(new FileReader(savePath));
            while(br.ready()) {
                builder.append(br.readLine());
            }
//            Gson saveFile = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
//                @Override
//                public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
//                    Instant instant = Instant.ofEpochMilli(json.getAsJsonPrimitive().getAsLong());
//                    return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
//                }
//            }).create();


//            saveFile = new Gson().fromJson(builder.toString(), SaveFile.class);
            saveFile = gson.fromJson(builder.toString(), SaveFile.class);

            if(saveFile == null) {
                saveFile = new SaveFile();
            }
        } catch (JsonSyntaxException e1){
            saveFile = new SaveFile();
            System.out.println("Corrupted save file!");
            return;
        }
        catch (IOException e2) {
            saveFile = new SaveFile();
            System.out.println("IO Error with save file!");
            return;
        }
        validateClientPath();
    }

    public void saveToDisk() {
        try {
//            StringBuilder builder = new StringBuilder();
            fw = new FileWriter(savePath);
//            Gson gson = new Gson();
//            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
//                @Override
//                public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
//                    Instant instant = Instant.ofEpochMilli(json.getAsJsonPrimitive().getAsLong());
//                    return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
//                }
//            }).create();
            fw.write(gson.toJson(saveFile));
            fw.close();
            //TODO : REMOVE
            this.loadFromDisk();
        } catch (IOException e) {
            return;
        }
    }

    public void validateClientPath() {
        String clientPath = saveFile.clientPath;


        if(clientPath != null) {
            File file = new File(clientPath);
            if (file.exists() && file.isFile()) {
                saveFile.validClientPath = true;
            }
        }
        if(!saveFile.validClientPath) {
            String[] commonDrives = {"C", "D", "E", "F"};
            String clientSteamStub =  ":/Program Files (x86)/Steam/steamapps/common/Path of Exile/logs/Client.txt";
            String clientStandAloneStub =  ":/Program Files (x86)/Grinding Gear Games/Path of Exile/logs/Client.txt";
            for(String drive : commonDrives){
                File clientFile = new File(drive + clientSteamStub);
                if(clientFile.exists() && clientFile.isFile()){
                    saveFile.validClientPath = true;
                    saveFile.clientPath = drive + clientSteamStub;
                    saveFile.clientDirectory = saveFile.clientPath.replaceFirst("Client\\.txt", "");
                    saveFile.clientCount++;
                    App.debugger.log("Valid client path found on " + drive + " drive. (Steam)");
                }
            }
            for(String drive : commonDrives){
                File clientFile = new File(drive + clientStandAloneStub);
                if(clientFile.exists() && clientFile.isFile()){
                    saveFile.validClientPath = true;
                    saveFile.clientPath = drive + clientStandAloneStub;
                    saveFile.clientDirectory = saveFile.clientPath.replaceFirst("Client\\.txt", "");
                    saveFile.clientCount++;
                    App.debugger.log("Valid client path found on " + drive + " drive. (Stand Alone)");
                }
            }

        }
    }

}
