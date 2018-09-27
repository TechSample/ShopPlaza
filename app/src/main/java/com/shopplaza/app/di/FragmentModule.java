package com.shopplaza.app.di;

import com.shopplaza.app.fragments.HomeFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract HomeFragment contributeHomeFragment();
}
