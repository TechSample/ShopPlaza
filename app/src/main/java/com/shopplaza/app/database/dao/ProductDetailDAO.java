package com.shopplaza.app.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomWarnings;

import com.shopplaza.app.model.Product;
import com.shopplaza.app.model.ProductDetailData;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ProductDetailDAO {

    @Insert(onConflict = REPLACE)
    public void insertProduct(ProductDetailData productDetailData);

    @Insert(onConflict = REPLACE)
    public void insertAllProducts(List<ProductDetailData> productDetailDataList);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT *"+
            "FROM " +
            "Product " +
            "LEFT JOIN " +
            "ProductDetailData " +
            "ON " +
            "Product.id = ProductDetailData.id "+
            "where " +
            "ProductDetailData.orderCount in (select orderCount from ProductDetailData) order by ProductDetailData.orderCount")
    public List<Product> getProductsByOrderCount();


    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT *"+
            "FROM " +
            "Product " +
            "LEFT JOIN " +
            "ProductDetailData " +
            "ON " +
            "Product.id = ProductDetailData.id "+
            "where " +
            "ProductDetailData.orderCount in (select viewCount from ProductDetailData) order by ProductDetailData.viewCount")
    public List<Product> getProductsByViewCount();



    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT *"+
            "FROM " +
            "Product " +
            "LEFT JOIN " +
            "ProductDetailData " +
            "ON " +
            "Product.id = ProductDetailData.id "+
            "where " +
            "ProductDetailData.orderCount in (select shares from ProductDetailData) order by ProductDetailData.shares")
    public List<Product> getProductsByShareCount();


    @Query("UPDATE ProductDetailData SET " +
            "id = :id,viewCount = :view_count where id = :id ;")
    public void UpdateViewCount(int id,int view_count);


    @Query("UPDATE ProductDetailData SET " +
            "id = :id,orderCount = :order_count where id = :id ;")
    public void UpdateOrderCount(int id,int order_count);

    @Query("UPDATE ProductDetailData SET " +
            "id = :id,shares= :shares where id = :id ;")
    public void UpdateShareCount(int id,int shares);

}
