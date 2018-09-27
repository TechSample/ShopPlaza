package com.shopplaza.app.activities;

import android.arch.persistence.room.Room;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.shopplaza.app.R;
import com.shopplaza.app.activities.BaseActivity;
import com.shopplaza.app.api.ApiClient;
import com.shopplaza.app.api.ApiService;
import com.shopplaza.app.database.dao.CategoryDAO;
import com.shopplaza.app.database.ShopPlazaDatabase;
import com.shopplaza.app.fragments.BaseFragment;
import com.shopplaza.app.fragments.HomeFragment;
import com.shopplaza.app.model.Category;
import com.shopplaza.app.model.DashboardDataResponse;
import com.shopplaza.app.model.ProductDetailData;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends BaseActivity implements HasSupportFragmentInjector{


    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    ShopPlazaDatabase database;
    Executor executor;
    CategoryDAO dao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configureDagger();
        if (savedInstanceState == null) {
            loadFragment(R.id.fragment_frame, new HomeFragment(), getResources().getString(R.string.home), 0, 0, BaseFragment.FragmentTransactionType.REPLACE);
        }
        getSupportActionBar().setLogo(R.mipmap.ic_launcher_round);
    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    private void configureDagger(){
        AndroidInjection.inject(this);
    }
}
