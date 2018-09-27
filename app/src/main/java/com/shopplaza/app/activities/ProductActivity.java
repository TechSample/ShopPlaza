package com.shopplaza.app.activities;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.shopplaza.app.R;
import com.shopplaza.app.adapter.ProductDetailAdapter;
import com.shopplaza.app.model.Product;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.http.GET;

public class ProductActivity extends BaseActivity {


    Product mProduct;

    @BindView(R.id.productName)
    TextView mTxtProductName;

    @BindView(R.id.recycler_view)
    RecyclerView mVariantListView;

    ProductDetailAdapter mAdapter;

   /* @BindView(R.id.anim_toolbar)
    Toolbar toolbar;*/



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);
       // setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(getIntent().getSerializableExtra("product")!= null){
            mProduct = (Product) getIntent().getSerializableExtra("product");
           // getSupportActionBar().setTitle(mProduct.getName());
           // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setTitle(mProduct.getName());
        if(mProduct.getVariants()!= null && mProduct.getVariants().size()> 0){
            mTxtProductName.setText(getResources().getString(R.string.available_variants));
        }else{
            mTxtProductName.setText(getResources().getString(R.string.no_variants));
        }

        mAdapter = new ProductDetailAdapter(mProduct.getVariants(),this);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(ProductActivity.this);
        mVariantListView.setLayoutManager(mLayoutManager);
        mVariantListView.setItemAnimator(new DefaultItemAnimator());
        mVariantListView.setAdapter(mAdapter);
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
