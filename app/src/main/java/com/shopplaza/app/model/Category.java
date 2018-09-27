
package com.shopplaza.app.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.shopplaza.app.database.TypeConverters.ChildCategoryConverter;
import com.shopplaza.app.database.TypeConverters.ProductConverter;

@Entity
public class Category implements Serializable{

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;


    @SerializedName("products")
    @Expose
    @TypeConverters(ProductConverter.class)
    private List<Product> products = null;

    @TypeConverters(ChildCategoryConverter.class)
    @SerializedName("child_categories")
    @Expose
    private List<Integer> childCategories = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Integer> getChildCategories() {
        return childCategories;
    }

    public void setChildCategories(List<Integer> childCategories) {
        this.childCategories = childCategories;
    }

}
