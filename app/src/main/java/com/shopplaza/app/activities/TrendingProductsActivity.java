package com.shopplaza.app.activities;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.shopplaza.app.R;
import com.shopplaza.app.ShopPlazaApp;
import com.shopplaza.app.adapter.CategoryListAdapter;
import com.shopplaza.app.adapter.TrendingListDetailAdapter;
import com.shopplaza.app.database.ShopPlazaDatabase;
import com.shopplaza.app.database.dao.ProductDetailDAO;
import com.shopplaza.app.model.Product;
import com.shopplaza.app.repositories.HomeDataRepository;
import com.shopplaza.app.viewmodels.HomeViewModel;
import com.shopplaza.app.viewmodels.TrendingViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.HasSupportFragmentInjector;

public class TrendingProductsActivity extends BaseActivity implements HasActivityInjector {

    private String currentindex;
    private TextView mSortList;
    private Spinner filterSpinner;
    private ArrayAdapter<String> spnAdapter;
    List<String> filterList;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    TrendingViewModel trendingViewModel;
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    List<Product> mostViewedProuctsList;
    List<Product> mostOrderedProuctsList;

    List<Product> currentIndexList;

    RecyclerView recyclerView;
    TrendingListDetailAdapter mAdapter;
    public boolean isAscending = true;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending_products);
        configureDagger();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.trending));

        trendingViewModel = ViewModelProviders.of(this,viewModelFactory).get(TrendingViewModel.class);

        mSortList = findViewById(R.id.txtSort);
        filterSpinner = findViewById(R.id.spinnerIndex);
        recyclerView = findViewById(R.id.recycler_view);
        filterList = new ArrayList<>();
        filterList.add(getResources().getString(R.string.most_viewed));
        filterList.add(getResources().getString(R.string.most_shared));
        filterList.add(getResources().getString(R.string.most_ordered));
        spnAdapter = new ArrayAdapter<String>(this,
                R.layout.row_spinner_filter, R.id.txtSpnDuration, filterList);
        spnAdapter.setDropDownViewResource(R.layout.row_spinner_filter);
        filterSpinner.setAdapter(spnAdapter);
        if (getIntent().getStringExtra("isFrom") != null) {
            currentindex = getIntent().getStringExtra("isFrom");
            int spinnerPosition = spnAdapter.getPosition(currentindex);
            if (currentindex.equalsIgnoreCase(getResources().getString(R.string.most_viewed))) {
                filterSpinner.setSelection(spinnerPosition);
            } else if (currentindex.equalsIgnoreCase(getResources().getString(R.string.most_shared))) {
                filterSpinner.setSelection(spinnerPosition);
            } else {
                filterSpinner.setSelection(spinnerPosition);
            }
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        try{

            if (currentindex.equalsIgnoreCase(getResources().getString(R.string.most_viewed))) {
                currentIndexList = trendingViewModel.getMostViewedProducts();
            } else if (currentindex.equalsIgnoreCase(getResources().getString(R.string.most_shared))) {
                currentIndexList = trendingViewModel.getMostSharedProducts();
            } else {
                currentIndexList = trendingViewModel.getMostOrderedProducts();
            }
            Toast.makeText(this,"Found "+currentIndexList.size()+" Products",Toast.LENGTH_SHORT).show();
            Collections.sort(currentIndexList, new Comparator<Product>(){
                public int compare(Product obj1, Product obj2) {
                    return Integer.compare(obj1.getVariants().get(0).getPrice(),obj2.getVariants().get(0).getPrice());
                }
            });
            mAdapter =  new TrendingListDetailAdapter(currentIndexList,this);
            final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            recyclerView.setVisibility(View.VISIBLE);
        }catch(Exception e){
            e.printStackTrace();
        }

        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mAdapter = null;
                if(position == 0){
                    currentIndexList = trendingViewModel.getMostViewedProducts();

                }else if(position == 1){
                    currentIndexList = trendingViewModel.getMostSharedProducts();

                }else{
                    currentIndexList = trendingViewModel.getMostOrderedProducts();

                }
                Toast.makeText(TrendingProductsActivity.this,"Found "+currentIndexList.size()+" Products",Toast.LENGTH_SHORT).show();
                Collections.sort(currentIndexList, new Comparator<Product>(){
                    public int compare(Product obj1, Product obj2) {
                        return Integer.compare(obj1.getVariants().get(0).getPrice(),obj2.getVariants().get(0).getPrice());
                    }
                });


               mAdapter =  new TrendingListDetailAdapter(currentIndexList,TrendingProductsActivity.this);
                final LinearLayoutManager mLayoutManager = new LinearLayoutManager(TrendingProductsActivity.this);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSortList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isAscending){
                    isAscending = false;
                    Collections.sort(currentIndexList, new Comparator<Product>(){
                        public int compare(Product obj1, Product obj2) {
                            return Integer.compare(obj2.getVariants().get(0).getPrice(),obj1.getVariants().get(0).getPrice());
                        }
                    });
                    mAdapter.notifyDataSetChanged();
                }else{

                    isAscending = true;
                    Collections.sort(currentIndexList, new Comparator<Product>(){
                        public int compare(Product obj1, Product obj2) {
                            return Integer.compare(obj1.getVariants().get(0).getPrice(),obj2.getVariants().get(0).getPrice());
                        }
                    });
                    mAdapter.notifyDataSetChanged();
                }
            }
        });


    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }


    private static class getAllAsyncTask extends android.os.AsyncTask<Void, Void, List<Product>> {

        private ProductDetailDAO mAsyncTaskDao;
        List<Product> a;

        getAllAsyncTask(ProductDetailDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected List<Product> doInBackground(Void... voids) {
            return mAsyncTaskDao.getProductsByOrderCount();
        }

        @Override
        protected void onPostExecute(List<Product> products) {
            super.onPostExecute(products);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);

    }

    private void configureDagger(){
        AndroidInjection.inject(this);
    }


}
