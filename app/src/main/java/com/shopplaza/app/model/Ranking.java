
package com.shopplaza.app.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.shopplaza.app.database.TypeConverters.ProductDetaildataConverter;

@Entity(indices = @Index(value = {"ranking"}, unique = true))
public class Ranking implements Serializable{

    @PrimaryKey(autoGenerate = true)
    public Integer id;

    @SerializedName("ranking")
    @Expose
    private String ranking;
    @TypeConverters(ProductDetaildataConverter.class)
    @SerializedName("products")
    @Expose
    private List<ProductDetailData> products = null;

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public List<ProductDetailData> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDetailData> products) {
        this.products = products;
    }

}
