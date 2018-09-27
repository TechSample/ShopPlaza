package com.shopplaza.app.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.shopplaza.app.model.Product;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ProductDAO {


    @Insert(onConflict = REPLACE)
    public void insertProduct(Product product);


    @Insert(onConflict = REPLACE)
    public void insertAllProducts(List<Product> productList);


    @Query("SELECT * FROM Product")
    List<Product> getProducts();
}
