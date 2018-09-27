package com.shopplaza.app.database.TypeConverters;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shopplaza.app.model.ProductDetailData;

import java.lang.reflect.Type;
import java.util.List;

public class ProductDetaildataConverter {


    @TypeConverter
    public static List<ProductDetailData> stringToProductDetailDatas(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<ProductDetailData>>() {}.getType();
        List<ProductDetailData> ProductDetailDatas = gson.fromJson(json, type);
        return ProductDetailDatas;
    }

    @TypeConverter
    public static String ProductDetailDatasToString(List<ProductDetailData> list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<ProductDetailData>>() {}.getType();
        String json = gson.toJson(list, type);
        return json;
    }
}
