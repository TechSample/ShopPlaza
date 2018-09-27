package com.shopplaza.app.database.TypeConverters;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shopplaza.app.model.Tax;

import java.lang.reflect.Type;
import java.util.List;

public class TaxConverter {

    @TypeConverter
    public static Tax stringToProducts(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<Tax>() {}.getType();
        Tax products = gson.fromJson(json, type);
        return products;
    }

    @TypeConverter
    public static String productsToString(Tax list) {
        Gson gson = new Gson();
        Type type = new TypeToken<Tax>() {}.getType();
        String json = gson.toJson(list, type);
        return json;
    }
}
