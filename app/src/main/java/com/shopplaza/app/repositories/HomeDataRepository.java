package com.shopplaza.app.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;

import com.shopplaza.app.ShopPlazaApp;
import com.shopplaza.app.api.ApiService;
import com.shopplaza.app.database.ShopPlazaDatabase;
import com.shopplaza.app.database.dao.CategoryDAO;
import com.shopplaza.app.di.HomeComponent;
import com.shopplaza.app.di.HomeDataModule;
import com.shopplaza.app.model.Category;
import com.shopplaza.app.model.DashboardDataResponse;
import com.shopplaza.app.model.Product;
import com.shopplaza.app.model.ProductDetailData;
import com.shopplaza.app.model.Ranking;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeDataRepository {


    public   ApiService mApiService;
    public   ShopPlazaDatabase database;
    Executor executor;

    private HomeDataListener mHomeDataListener;
    private LiveData<List<Category>> categories;
    private List<Ranking> rankings;
    Context mContext;

    @Inject
    public HomeDataRepository(ApiService service, Executor executor, ShopPlazaDatabase database){
        this.mApiService = service;
        this.executor = executor;
        this.database = database;
    }



    public List<Category> getCategories(){

        List<Category> categories = getAllCategories();
        return categories;
    }

    public List<Ranking> getRankings(){

        List<Ranking> rankings = getAllRankings();
        return rankings;
    }

    public List<Product> getMostOrderedProducts(){

        List<Product> products = getAllOrderedProducts();
        return products;
    }


    public List<Product> getMostViewedProducts(){

        List<Product> products = getAllViewedProducts();
        return products;
    }


    public List<Product> getMostSharedProducts(){

        List<Product> products = getAllSharedProducts();
        return products;
    }

    public void getHomeData(HomeDataListener listener,Context context){

        mHomeDataListener = listener;
        mContext = context;

        Call<DashboardDataResponse> dashboardApiCall = mApiService.getDashboardData();
        dashboardApiCall.enqueue(new Callback<DashboardDataResponse>() {
            @Override
            public void onResponse(Call<DashboardDataResponse> call, final Response<DashboardDataResponse> response) {

                System.out.println("Response success"+response.code());
                if(response.code() == 200){

                    executor.execute(() -> {


                        if(response.body().getCategories()!= null && response.body().getCategories().size()> 0){
                            System.out.println("Data getCategories size"+response.body().getCategories().size());
                            database.categoryDAO().insertAllCategory(response.body().getCategories());

                            for(int i = 0;i<response.body().getCategories().size();i++){
                                Category category = response.body().getCategories().get(i);
                                database.productDAO().insertAllProducts(response.body().getCategories().get(i).getProducts());

                                if(category.getProducts()!= null && category.getProducts().size()>0){
                                    System.out.println("Data getProducts size"+category.getProducts().size());
                                    for(int productIndex = 0; productIndex < category.getProducts().size();productIndex++){
                                       // database.productDAO().insertProduct(category.getProducts().get(productIndex));

                                        if(category.getProducts().get(productIndex).getVariants()!= null && category.getProducts().get(productIndex).getVariants().size()>0){
                                            System.out.println("Data getVariants size"+category.getProducts().get(productIndex).getVariants().size());
                                            database.variantDAO().insertAllVariants(category.getProducts().get(productIndex).getVariants());
                                            /*for(int variantIndex = 0; variantIndex < category.getProducts().get(productIndex).getVariants().size();variantIndex++){
                                              //  database.variantDAO().insertVariant(category.getProducts().get(productIndex).getVariants().get(variantIndex));
                                            }*/
                                        }


                                        if(category.getProducts().get(productIndex).getTax()!= null){
                                            System.out.println("Data getTax"+category.getProducts().get(productIndex).getTax());
                                            database.taxDAO().insertTax(category.getProducts().get(productIndex).getTax());
                                        }
                                    }

                                }




                            }

                        }


                        if(response.body().getRankings()!= null && response.body().getRankings().size()>0) {

                            database.rankingDAO().insertAllRankings(response.body().getRankings());

                            for (int i = 0; i < response.body().getRankings().size(); i++) {

                                if (response.body().getRankings().get(i).getRanking() != null) {


                                    if (response.body().getRankings().get(i).getRanking().contains("Viewed")) {
                                        if (response.body().getRankings().get(i).getProducts() != null &&
                                                response.body().getRankings().get(i).getProducts().size() > 0) {

                                            for (int rankProduct = 0; rankProduct < response.body().getRankings().get(i).getProducts().size(); rankProduct++) {

                                                ProductDetailData data = new ProductDetailData();
                                                data.setId(response.body().getRankings().get(i).getProducts().get(rankProduct).getId());
                                                data.setRankType(0);
                                                data.setViewCount(response.body().getRankings().get(i).getProducts().get(rankProduct).getViewCount());
                                                 database.mostViewedDAO().insertViewedProducts(data);

                                                // database.mostViewedDAO().insertViewedProducts(response.body().getRankings().get(i).getProducts().get(rankProduct));

                                            }
                                        }
                                    } else if (response.body().getRankings().get(i).getRanking().contains("ShaRed")) {
                                        if (response.body().getRankings().get(i).getProducts() != null &&
                                                response.body().getRankings().get(i).getProducts().size() > 0) {

                                            for (int rankProduct = 0; rankProduct < response.body().getRankings().get(i).getProducts().size(); rankProduct++) {

                                                ProductDetailData data = new ProductDetailData();
                                                data.setId(response.body().getRankings().get(i).getProducts().get(rankProduct).getId());
                                                data.setRankType(1);
                                                data.setShares(response.body().getRankings().get(i).getProducts().get(rankProduct).getShares());
                                                database.mostSharedDAO().insertSharedProducts(data);
                                               // database.mostSharedDAO().insertSharedProducts(response.body().getRankings().get(i).getProducts().get(rankProduct));

                                            }
                                        }

                                    } else {

                                        if (response.body().getRankings().get(i).getProducts() != null &&
                                                response.body().getRankings().get(i).getProducts().size() > 0) {

                                            for (int rankProduct = 0; rankProduct < response.body().getRankings().get(i).getProducts().size(); rankProduct++) {


                                                ProductDetailData data = new ProductDetailData();
                                                data.setId(response.body().getRankings().get(i).getProducts().get(rankProduct).getId());
                                                data.setRankType(2);
                                                data.setOrderCount(response.body().getRankings().get(i).getProducts().get(rankProduct).getOrderCount());
                                                database.mostSharedDAO().insertSharedProducts(data);
                                              //  database.mostOrderedDAO().insertOrderedProducts(response.body().getRankings().get(i).getProducts().get(rankProduct));

                                            }
                                        }
                                    }
                                }

                            }


                        }

                        if(mHomeDataListener != null){
                            mHomeDataListener.setHomeData(response.body().getCategories(),response.body().getRankings());
                       }


                    });


                }
            }

            @Override
            public void onFailure(Call<DashboardDataResponse> call, Throwable t) {
                //   System.out.println("Response onFailure "+t.getCause().getMessage());
                System.out.println("Response fail");
                if(mHomeDataListener != null){
                    mHomeDataListener.onError(t.getCause().getMessage());
                }

            }
        });

    }

    public interface HomeDataListener{

        void setHomeData(List<Category> categories,List<Ranking> rankings);
        void onError(String errorMessage);
    }



    private class GetCategoriesAsyncTask extends AsyncTask<Void, Void,List<Category>>
    {
        @Override
        protected List<Category> doInBackground(Void... url) {
            return database.categoryDAO().getCategories();
        }
    }

    public List<Category> getAllCategories(){
        try{
            return new GetCategoriesAsyncTask().execute().get();
        }catch (Exception e){
            return null;
        }
    }


    private class GetRankingsAsyncTask extends AsyncTask<Void, Void,List<Ranking>>
    {
        @Override
        protected List<Ranking> doInBackground(Void... url) {
            return database.rankingDAO().getRankings();
        }
    }

    public List<Ranking> getAllRankings(){
        try{
            return new GetRankingsAsyncTask().execute().get();

        }catch (Exception e){
            return null;
        }
    }


    private class GetMostOrderedAsyncTask extends AsyncTask<Void, Void,List<Product>>
    {
        @Override
        protected List<Product> doInBackground(Void... url) {
          //  return database.productDetailDAO().getProductsByShareCount();
            return database.mostOrderedDAO().getMostOrderedProducts(2);
        }
    }


    private class GetMostViewedAsyncTask extends AsyncTask<Void, Void,List<Product>>
    {
        @Override
        protected List<Product> doInBackground(Void... url) {
            //  return database.productDetailDAO().getProductsByShareCount();
            return database.mostViewedDAO().getMostViewedProducts(0);
        }
    }



    private class GetMostSharedAsyncTask extends AsyncTask<Void, Void,List<Product>>
    {
        @Override
        protected List<Product> doInBackground(Void... url) {
            //  return database.productDetailDAO().getProductsByShareCount();
            return database.mostSharedDAO().getMostSharedProducts(1);
        }
    }

    private List<Product> getAllOrderedProducts(){
        try{
            return new GetMostOrderedAsyncTask().execute().get();

        }catch (Exception e){
            return null;
        }
    }


    private List<Product> getAllViewedProducts(){
        try{
            return new GetMostViewedAsyncTask().execute().get();

        }catch (Exception e){
            return null;
        }
    }


    private List<Product> getAllSharedProducts(){
        try{
            return new GetMostSharedAsyncTask().execute().get();

        }catch (Exception e){
            return null;
        }
    }
}
