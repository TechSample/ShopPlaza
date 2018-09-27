package com.shopplaza.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shopplaza.app.R;
import com.shopplaza.app.activities.ProductActivity;
import com.shopplaza.app.model.Product;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrendingListDetailAdapter extends RecyclerView.Adapter<TrendingListDetailAdapter.ListHolder> {

    private Context mContext;
    private List<Product> mProductList;

    @BindView(R.id.txtProduct)
    TextView txtProduct;

    @BindView(R.id.txtProductPrice)
    TextView txtProductPrice;


    @BindView(R.id.productListContainer)
    public LinearLayout mListContainer;


    public TrendingListDetailAdapter(List<Product> productList, Context context) {
        this.mProductList = productList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.trending_product_list_item, parent, false);

        ButterKnife.bind(this, view);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder holder, int position) {

        holder.setIsRecyclable(false);
        Product product = mProductList.get(position);
        txtProduct.setText(product.getName());
        try{
            txtProductPrice.setText(mContext.getResources().getString(R.string.Rs)+ product.getVariants().get(0).getPrice().toString());
        }catch (Exception e){
            e.printStackTrace();
        }


        mListContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mContext.startActivity(new Intent(mContext, ProductActivity.class)
                        .putExtra("product",product));
            }
        });

    }


    @Override
    public int getItemCount() {
        return mProductList.size();
    }

    public static class ListHolder extends RecyclerView.ViewHolder {


        public ListHolder(View view) {
            super(view);


        }

    }
}
