package com.slimtrade.core.SaveSystem;

import com.google.gson.*;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class AbstractSaveFile<T> {

    public String path;
    private final Class<T> classType;
//    private final Class<T> save;
    FileWriter fw;
    String savePath = "";
    Gson gson;

    public AbstractSaveFile(Class<T> classType) {
        this.classType = classType;
        gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                Instant instant = Instant.ofEpochMilli(json.getAsJsonPrimitive().getAsLong());
//                return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                return ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toLocalDateTime();
            }
        }).create();
    }

    public void loadFromDisk() {

    }

    public void saveToDisk() {
        try {
            fw = new FileWriter(savePath);
            fw.write(gson.toJson(classType));
            fw.close();
        } catch (IOException e) {
            return;
        }
    }


}
