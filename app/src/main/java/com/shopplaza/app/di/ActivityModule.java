package com.shopplaza.app.di;

import com.shopplaza.app.activities.DashboardActivity;
import com.shopplaza.app.activities.TrendingProductsActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector(modules = FragmentModule.class)
    abstract DashboardActivity contributeDashboardActivity();

    @ContributesAndroidInjector(modules = FragmentModule.class)
    abstract TrendingProductsActivity contributeTrendingProductsActivity();
}
