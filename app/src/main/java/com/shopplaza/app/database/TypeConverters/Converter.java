package com.shopplaza.app.database.TypeConverters;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shopplaza.app.model.Product;

import java.lang.reflect.Type;
import java.util.List;
import java.util.List;

public class Converter {
    @TypeConverter
    public static List<String> fromString(String value) {
        Type listType = new TypeToken<List<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }



   /* @TypeConverter
    public static List<Integer> fromInteger(Integer value) {
        Type listType = new TypeToken<List<Integer>>() {}.getType();
        return new Gson().fromJson(String.valueOf(value), listType);
    }*/

    /*@TypeConverter
    public static String fromIntegerList(List<Integer> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }


    @TypeConverter
    public static List<Object> fromObject(Object value) {
        Type listType = new TypeToken<List<Object>>() {}.getType();
        return new Gson().fromJson(String.valueOf(value), listType);
    }*/

    /*@TypeConverter
    public static String fromObjectList(List<Object> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }*/

}