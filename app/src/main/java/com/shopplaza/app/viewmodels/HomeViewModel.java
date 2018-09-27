package com.shopplaza.app.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.shopplaza.app.model.Category;
import com.shopplaza.app.model.Ranking;
import com.shopplaza.app.repositories.HomeDataRepository;

import java.util.List;

import javax.inject.Inject;

public class HomeViewModel extends ViewModel {

    private LiveData<List<Category>> mCategoryList;

    public HomeDataRepository homeDataRepository;

    @Inject
    public HomeViewModel(HomeDataRepository userRepo) {
        this.homeDataRepository = userRepo;
    }


    public void loadHomeData(HomeDataRepository.HomeDataListener listener, Context context) {
        this.homeDataRepository.getHomeData(listener,context);

    }

    /*public List<Category> getCategories(){

       return  this.homeDataRepository.getCategories();
    }
*/
    public List<Ranking> getRankingProducts(){

        return this.homeDataRepository.getRankings();
    }

    public List<Category> getCategories(){

        return  this.homeDataRepository.getCategories();
    }
}
