package com.shopplaza.app.database.TypeConverters;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ObjectConverter {

    @TypeConverter
    public static List<Object> stringToObjects(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Object>>() {}.getType();
        List<Object> Objects = gson.fromJson(json, type);
        return Objects;
    }

    @TypeConverter
    public static String ObjectsToString(List<Object> list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Object>>() {}.getType();
        String json = gson.toJson(list, type);
        return json;
    }
}
