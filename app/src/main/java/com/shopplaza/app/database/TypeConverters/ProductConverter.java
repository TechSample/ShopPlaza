package com.shopplaza.app.database.TypeConverters;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shopplaza.app.model.Product;

import java.lang.reflect.Type;
import java.util.List;

public class ProductConverter {

    @TypeConverter
    public static List<Product> stringToProducts(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Product>>() {}.getType();
        List<Product> products = gson.fromJson(json, type);
        return products;
    }

    @TypeConverter
    public static String productsToString(List<Product> list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Product>>() {}.getType();
        String json = gson.toJson(list, type);
        return json;
    }
}


