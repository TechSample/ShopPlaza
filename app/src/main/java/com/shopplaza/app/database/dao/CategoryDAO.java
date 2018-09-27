package com.shopplaza.app.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.shopplaza.app.model.Category;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface CategoryDAO {

    @Insert(onConflict = REPLACE)
    public void insertCategory(Category cat);

    @Insert(onConflict = REPLACE)
    public void insertAllCategory(List<Category> categoryList);

    @Query("SELECT * FROM Category")
    List<Category> getCategories();


    @Query("SELECT * FROM Category")
    LiveData<List<Category>> getLiveDataCategories();
}
