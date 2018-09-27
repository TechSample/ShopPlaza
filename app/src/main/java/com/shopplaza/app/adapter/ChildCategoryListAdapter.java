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
import com.shopplaza.app.activities.CategoryActivity;
import com.shopplaza.app.activities.ChildCategoryActivity;
import com.shopplaza.app.model.Category;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChildCategoryListAdapter extends RecyclerView.Adapter<ChildCategoryListAdapter.ListHolder> {

    private Context mContext;
    private List<Category> mCategoryList;
    private List<Category> mDbCategoryList;

    @BindView(R.id.txtCategory)
    TextView txtCategory;

    @BindView(R.id.listContainer)
    public LinearLayout mListContainer;


    public ChildCategoryListAdapter(List<Category> categoryList,List<Category> dbCategoryList, Context context) {
        this.mCategoryList = categoryList;
        this.mContext = context;
        this.mDbCategoryList = dbCategoryList;
    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.category_grid_item, parent, false);

        ButterKnife.bind(this, view);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder holder, int position) {

        holder.setIsRecyclable(false);
        Category category = mCategoryList.get(position);
        txtCategory.setText(category.getName());

        mListContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Category> subCategories =  new ArrayList<>();

                if(category.getChildCategories()!= null &&category.getChildCategories().size() > 0 ){
                    List<Integer> childCategories = category.getChildCategories();
                    for(int i=0;i<childCategories.size();i++){
                        Integer categoryId =  childCategories.get(i);
                        for(int j=0;j<mDbCategoryList.size();j++){
                            if(mDbCategoryList.get(j).getId().equals(categoryId)){
                                subCategories.add(mDbCategoryList.get(j));
                            }
                        }
                    }
                    System.out.println("SubCategory "+subCategories.size()+ "db size "+mDbCategoryList.size());
                    mContext.startActivity(new Intent(mContext, ChildCategoryActivity.class)
                            .putExtra("categoryList", (Serializable) subCategories)
                            .putExtra("categoryName",category.getName())
                            .putExtra("dbCategoryList", (Serializable) mDbCategoryList));
                }else{
                    mContext.startActivity(new Intent(mContext, CategoryActivity.class)
                            .putExtra("productList", (Serializable) category.getProducts())
                            .putExtra("categoryName",category.getName()));
                }

            }
        });

    }


    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }

    public static class ListHolder extends RecyclerView.ViewHolder {


        public ListHolder(View view) {
            super(view);


        }

    }
}
