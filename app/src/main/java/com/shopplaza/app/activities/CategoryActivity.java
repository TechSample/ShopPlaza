package com.shopplaza.app.activities;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.shopplaza.app.R;
import com.shopplaza.app.adapter.CategoryDetailAdapter;
import com.shopplaza.app.adapter.CategoryListAdapter;
import com.shopplaza.app.model.Product;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryActivity extends BaseActivity{


    List<Product> mProductList;
    String mCategoryName;

    @BindView(R.id.productListView)
    RecyclerView mProductListView;

    @BindView(R.id.noResultLayout)
    LinearLayout mNoResults;

    CategoryDetailAdapter mAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);
        if(getIntent().getSerializableExtra("productList")!= null){
            mProductList = (List<Product>) getIntent().getSerializableExtra("productList");
        }
        if(getIntent().getStringExtra("categoryName")!= null){
            mCategoryName = getIntent().getStringExtra("categoryName");
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(mCategoryName);

        mAdapter = new CategoryDetailAdapter(mProductList,CategoryActivity.this);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(CategoryActivity.this);
        mProductListView.setLayoutManager(mLayoutManager);
        mProductListView.setItemAnimator(new DefaultItemAnimator());
        mProductListView.setAdapter(mAdapter);

        /*if(mProductList.size() == 0){
            mProductListView.setVisibility(View.GONE);
            mNoResults.setVisibility(View.VISIBLE);
        }*/

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);

    }
}



