package com.shopplaza.app.di;

import android.app.Application;
import android.app.Fragment;

import com.shopplaza.app.ShopPlazaApp;
import com.shopplaza.app.fragments.BaseFragment;
import com.shopplaza.app.repositories.HomeDataRepository;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Component(modules = {HomeDataModule.class,ActivityModule.class,FragmentModule.class})
@Singleton
public interface HomeComponent {


    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        HomeComponent build();
    }

    void inject(ShopPlazaApp app);
}
