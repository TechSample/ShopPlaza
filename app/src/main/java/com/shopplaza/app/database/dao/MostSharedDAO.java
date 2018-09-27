package com.shopplaza.app.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomWarnings;

import com.shopplaza.app.model.Product;
import com.shopplaza.app.model.ProductDetailData;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface MostSharedDAO {

    @Insert(onConflict = REPLACE)
    public void insertSharedProducts(ProductDetailData productDetailData);


    /*@Query("SELECT * FROM ProductDetailData WHERE :type = 1")
    List<ProductDetailData> getMostSharedProducts(int type);

*/
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT *"+
            "FROM " +
            "Product " +
            "LEFT JOIN " +
            "ProductDetailData " +
            "ON " +
            "Product.id = ProductDetailData.id "+
            "where ProductDetailData.rankType = :type")
        // @Query("SELECT * FROM ProductDetailData WHERE :type = 2")
    List<Product> getMostSharedProducts(int type);

}
