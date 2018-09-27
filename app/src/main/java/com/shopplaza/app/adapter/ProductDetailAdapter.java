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
import com.shopplaza.app.model.Variant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductDetailAdapter extends RecyclerView.Adapter<ProductDetailAdapter.ListHolder> {

    private Context mContext;
    private List<Variant> mProductVariantList;

    @BindView(R.id.color)
    TextView txtColor;

    @BindView(R.id.size)
    TextView txtSize;


    @BindView(R.id.price)
    TextView txtPrice;




    public ProductDetailAdapter(List<Variant> productVariantsList, Context context) {
        this.mProductVariantList = productVariantsList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.row_product_detail_item, parent, false);

        ButterKnife.bind(this, view);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder holder, int position) {

        holder.setIsRecyclable(false);
        Variant variant = mProductVariantList.get(position);
        txtColor.setText(variant.getColor());
        if(variant.getColor()!= null){
            txtColor.setText(variant.getColor());
        }else{
            txtColor.setText("-");
        }

        if(variant.getSize()!= null){
            txtSize.setText(String.valueOf(variant.getSize()));
        }else{
            txtSize.setText("-");
        }

        if(variant.getPrice()!= null){
            txtPrice.setText(mContext.getResources().getString(R.string.Rs)+String.valueOf(variant.getPrice()));
        }else{
            txtPrice.setText("-");
        }

      /*  mListContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mContext.startActivity(new Intent(mContext, ProductActivity.class)
                        .putExtra("product",product));
            }
        });*/

    }


    @Override
    public int getItemCount() {
        return mProductVariantList.size();
    }

    public static class ListHolder extends RecyclerView.ViewHolder {


        public ListHolder(View view) {
            super(view);


        }

    }
}
