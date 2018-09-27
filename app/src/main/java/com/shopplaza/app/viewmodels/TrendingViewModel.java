package com.shopplaza.app.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.shopplaza.app.model.Category;
import com.shopplaza.app.model.Product;
import com.shopplaza.app.model.Ranking;
import com.shopplaza.app.repositories.HomeDataRepository;

import java.util.List;

import javax.inject.Inject;

public class TrendingViewModel extends ViewModel {

    private LiveData<List<Category>> mCategoryList;

    public HomeDataRepository homeDataRepository;

    @Inject
    public TrendingViewModel(HomeDataRepository userRepo) {
        this.homeDataRepository = userRepo;
    }


    public List<Product> getMostViewedProducts(){

        return this.homeDataRepository.getMostViewedProducts();
    }

    public List<Product> getMostOrderedProducts(){

        return this.homeDataRepository.getMostOrderedProducts();
    }

    public List<Product> getMostSharedProducts(){

        return this.homeDataRepository.getMostSharedProducts();
    }
    public List<Category> getCategories(){

        return  this.homeDataRepository.getCategories();
    }
}
