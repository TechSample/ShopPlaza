package com.shopplaza.app.database.TypeConverters;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shopplaza.app.model.Variant;

import java.lang.reflect.Type;
import java.util.List;

public class VariantsConverter {

    @TypeConverter
    public static List<Variant> stringToVariants(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Variant>>() {}.getType();
        List<Variant> Variants = gson.fromJson(json, type);
        return Variants;
    }

    @TypeConverter
    public static String VariantsToString(List<Variant> list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Variant>>() {}.getType();
        String json = gson.toJson(list, type);
        return json;
    }
}
