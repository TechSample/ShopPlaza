package com.shopplaza.app.di;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shopplaza.app.api.ApiService;
import com.shopplaza.app.database.ShopPlazaDatabase;
import com.shopplaza.app.repositories.HomeDataRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
public class HomeDataModule {


    @Provides
    @Singleton
    HomeDataRepository provideHomeDataRepository(ApiService apiService,Executor executor, ShopPlazaDatabase database) {
        return new HomeDataRepository(apiService, executor, database);
    }

    @Provides
    @Singleton
    public ShopPlazaDatabase provideDatabase(Application application){

        return Room.databaseBuilder(application,
                ShopPlazaDatabase.class, "T4.db")
                .build();

    }

    @Provides
    Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    private static String BASE_URL = "https://stark-spire-93433.herokuapp.com/";

    @Provides
    Gson provideGson() { return new GsonBuilder().create(); }

    @Provides
    Retrofit provideRetrofit(Gson gson) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }


}
