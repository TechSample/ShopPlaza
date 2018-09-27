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
import com.shopplaza.app.adapter.ChildCategoryListAdapter;
import com.shopplaza.app.model.Category;
import com.shopplaza.app.model.Product;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChildCategoryActivity extends BaseActivity{


    List<Category> mCategoryList;
    String mCategoryName;

    @BindView(R.id.childCategoryListView)
    RecyclerView mChildCategoryListView;

    @BindView(R.id.noResultLayout)
    LinearLayout mNoResults;

    ChildCategoryListAdapter mAdapter;
    private List<Category> mDbCategoryList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_category);
        ButterKnife.bind(this);
        if(getIntent().getSerializableExtra("categoryList")!= null){
            mCategoryList = (List<Category>) getIntent().getSerializableExtra("categoryList");
        }
        if(getIntent().getStringExtra("categoryName")!= null){
            mCategoryName = getIntent().getStringExtra("categoryName");
        }

        if(getIntent().getSerializableExtra("dbCategoryList")!= null){
            mDbCategoryList = (List<Category>) getIntent().getSerializableExtra("dbCategoryList");
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(mCategoryName);

        mAdapter = new ChildCategoryListAdapter(mCategoryList,mDbCategoryList,ChildCategoryActivity.this);
        final GridLayoutManager mLayoutManager = new GridLayoutManager(ChildCategoryActivity.this,2);
        mChildCategoryListView.setLayoutManager(mLayoutManager);
        mChildCategoryListView.setItemAnimator(new DefaultItemAnimator());
        mChildCategoryListView.setAdapter(mAdapter);

        /*if(mCategoryList.size() == 0){
            mChildCategoryListView.setVisibility(View.GONE);
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



