package com.shopplaza.app.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.shopplaza.app.database.TypeConverters.ChildCategoryConverter;
import com.shopplaza.app.database.TypeConverters.Converter;
import com.shopplaza.app.database.TypeConverters.ObjectConverter;
import com.shopplaza.app.database.TypeConverters.ProductConverter;
import com.shopplaza.app.database.TypeConverters.TaxConverter;
import com.shopplaza.app.database.TypeConverters.VariantsConverter;
import com.shopplaza.app.database.dao.CategoryDAO;
import com.shopplaza.app.database.dao.MostOrderedDAO;
import com.shopplaza.app.database.dao.MostSharedDAO;
import com.shopplaza.app.database.dao.ProductDAO;
import com.shopplaza.app.database.dao.MostViewedDAO;
import com.shopplaza.app.database.dao.ProductDetailDAO;
import com.shopplaza.app.database.dao.RankingDAO;
import com.shopplaza.app.database.dao.TaxDAO;
import com.shopplaza.app.database.dao.VariantDAO;
import com.shopplaza.app.model.Category;
import com.shopplaza.app.model.Product;
import com.shopplaza.app.model.ProductDetailData;
import com.shopplaza.app.model.Ranking;
import com.shopplaza.app.model.Tax;
import com.shopplaza.app.model.Variant;

@Database(entities = {Category.class,Product.class,ProductDetailData.class,
        Variant.class,Tax.class, Ranking.class},version = 1, exportSchema = false)
@TypeConverters({Converter.class,ProductConverter.class,ChildCategoryConverter.class,
                VariantsConverter.class,TaxConverter.class,ObjectConverter.class
                })
public abstract class ShopPlazaDatabase extends RoomDatabase {

    public abstract CategoryDAO categoryDAO();

    public abstract ProductDAO productDAO();
    public abstract MostViewedDAO mostViewedDAO();
    public abstract MostSharedDAO mostSharedDAO();
    public abstract MostOrderedDAO mostOrderedDAO();


    public abstract TaxDAO taxDAO();
    public abstract VariantDAO variantDAO();
    public abstract RankingDAO rankingDAO();
    public abstract ProductDetailDAO productDetailDAO();
}
