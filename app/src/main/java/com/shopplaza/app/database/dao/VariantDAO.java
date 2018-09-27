package com.shopplaza.app.database.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import com.shopplaza.app.model.Variant;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface VariantDAO {


    @Insert(onConflict = REPLACE)
    public void insertVariant(Variant variant);

    @Insert(onConflict = REPLACE)
    public void insertAllVariants(List<Variant> variants);

    @Query("SELECT * FROM Variant")
    List<Variant> getVariants();
}
