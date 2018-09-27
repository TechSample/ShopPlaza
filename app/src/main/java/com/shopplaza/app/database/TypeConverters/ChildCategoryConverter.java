package com.shopplaza.app.database.TypeConverters;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ChildCategoryConverter {

    @TypeConverter
    public static List<Integer> stringToChildCategories(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Integer>>() {}.getType();
        List<Integer> childCategories = gson.fromJson(json, type);
        return childCategories;
    }

    @TypeConverter
    public static String childToString(List<Integer> list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Integer>>() {}.getType();
        String json = gson.toJson(list, type);
        return json;
    }
}
