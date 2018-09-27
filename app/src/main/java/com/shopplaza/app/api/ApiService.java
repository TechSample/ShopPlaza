package com.shopplaza.app.api;

import com.shopplaza.app.model.DashboardDataResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("json")
    Call<DashboardDataResponse> getDashboardData();
}
