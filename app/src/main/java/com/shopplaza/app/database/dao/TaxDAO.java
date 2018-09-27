package com.shopplaza.app.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import com.shopplaza.app.model.Tax;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface TaxDAO {


    @Insert(onConflict = REPLACE)
    public void insertTax(Tax tax);

    @Query("SELECT * FROM Tax")
    List<Tax> getTaxs();
}
