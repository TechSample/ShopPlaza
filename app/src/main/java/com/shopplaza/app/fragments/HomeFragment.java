package com.shopplaza.app.fragments;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.shopplaza.app.R;
import com.shopplaza.app.Utility.ItemDecorationAlbumColumns;
import com.shopplaza.app.Utility.ItemOffsetDecoration;
import com.shopplaza.app.Utility.NetworkUtility;
import com.shopplaza.app.Utility.SpaceItemDecoration;
import com.shopplaza.app.activities.TrendingProductsActivity;
import com.shopplaza.app.adapter.CategoryListAdapter;
import com.shopplaza.app.model.Category;
import com.shopplaza.app.model.Ranking;
import com.shopplaza.app.repositories.HomeDataRepository;
import com.shopplaza.app.viewmodels.HomeViewModel;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ViewListener;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class HomeFragment extends BaseFragment implements HomeDataRepository.HomeDataListener{


    private RecyclerView mCategoryListView;
    private CarouselView mCarouselView;
    private ShimmerFrameLayout mShimmerLayout;
    private TextView mTxtCarousel;
    private TextView mTxtCarouselType;
    private HomeViewModel mHomeViewModel;
    private CategoryListAdapter mAdapter;
    private List<Category> mCategoryList;
    private List<Ranking> mRankingList;
    private RelativeLayout mNoNetworkLayout;
    private TextView  shopHint;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    Executor executor;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home,container,false);
        mCarouselView = view.findViewById(R.id.carouselView);
        mCategoryListView = view.findViewById(R.id.recycler_view);
        mShimmerLayout = view.findViewById(R.id.shimmer_view_container);
        mNoNetworkLayout = view.findViewById(R.id.containerNoNetwork);
        shopHint = view.findViewById(R.id.shopHint);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        configureDagger();
        mHomeViewModel = ViewModelProviders.of(getActivity(),viewModelFactory).get(HomeViewModel.class);
        mCarouselView.setIndicatorGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM);
        mShimmerLayout.setVisibility(View.GONE);
        mNoNetworkLayout.findViewById(R.id.includeLayout)
                .findViewById(R.id.txtTryAgain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadData();
            }
        });
        initializeHomeData();

    }

    public void loadData(){

        if (NetworkUtility.isOnline(getActivity())) {
            mShimmerLayout.setVisibility(View.VISIBLE);
            mShimmerLayout.startShimmerAnimation();
            mNoNetworkLayout.setVisibility(View.GONE);
           // mNoDataFoundLayout.setVisibility(View.GONE);
            mHomeViewModel.loadHomeData(this, getActivity());
        } else {
            mShimmerLayout.setVisibility(View.GONE);
            mShimmerLayout.stopShimmerAnimation();
            mNoNetworkLayout.setVisibility(View.VISIBLE);
            shopHint.setVisibility(View.GONE);

        }
    }

    @Override
    public void setHomeData(List<Category> categories,List<Ranking> rankings) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                mShimmerLayout.setVisibility(View.GONE);
                mCategoryListView.setVisibility(View.VISIBLE);
                mCarouselView.setVisibility(View.VISIBLE);
                shopHint.setVisibility(View.VISIBLE);
                if (mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                }else{
                    mAdapter = new CategoryListAdapter(categories,getActivity());
                    final GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(),3);
                    mCategoryListView.setLayoutManager(mLayoutManager);
                    int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.default_indicator_margin_v);
                    mCategoryListView.setItemAnimator(new DefaultItemAnimator());
                    mCategoryListView.setAdapter(mAdapter);
                }
                setupCarousel(rankings);
            }
        });

    }

    @Override
    public void onError(String errorMessage) {


    }

    private void openTrendingIntent(String isFrom){

        Intent intent = new Intent();
        intent.setClass(getActivity(), TrendingProductsActivity.class);
        intent.putExtra("isFrom",isFrom);
        startActivity(intent);
    }

    private void configureDagger(){
        AndroidSupportInjection.inject(this);
    }

    private void initializeHomeData(){
        mNoNetworkLayout.setVisibility(View.GONE);
        mCategoryList =  mHomeViewModel.getCategories();
        mRankingList  =  mHomeViewModel.getRankingProducts();

        if(mCategoryList!= null && mCategoryList.size() > 0 ||
            mRankingList != null && mRankingList.size()> 0){

            System.out.println("Data Available in DB");
            shopHint.setVisibility(View.VISIBLE);
            mAdapter = new CategoryListAdapter(mCategoryList,getActivity());
            final GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(),3);
            mCategoryListView.setLayoutManager(mLayoutManager);
            mCategoryListView.setItemAnimator(new DefaultItemAnimator());
            mCategoryListView.setAdapter(mAdapter);
            mCategoryListView.setVisibility(View.VISIBLE);
            setupCarousel(mRankingList);
            mCarouselView.setVisibility(View.VISIBLE);
        }else{
            // do api call
            System.out.println("Data UnAvailable. Call API");
            loadData();
        }
    }


    private void setupCarousel(List<Ranking> rankings){


        mCarouselView.setViewListener(new ViewListener() {
            @Override
            public View setViewForPosition(int position) {
                View customView = getLayoutInflater().inflate(R.layout.row_slider, null);
                mTxtCarousel = customView.findViewById(R.id.txtTitle);
                mTxtCarouselType = customView.findViewById(R.id.txtCarouselType);

                if(position == 0){
                    customView = getLayoutInflater().inflate(R.layout.row_slider_most_viewed, null);
                    TextView text = customView.findViewById(R.id.txtCarousel);
                    text.setText("Trending Products");
                    ImageView sliderImage = customView.findViewById(R.id.sliderImage);
                    sliderImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            openTrendingIntent(getResources().getString(R.string.most_viewed));
                        }
                    });
                }else if(position == 1){
                    mTxtCarousel.setText(rankings.get(position).getRanking());
                    customView = getLayoutInflater().inflate(R.layout.row_slider_most_ordered, null);
                    TextView text = customView.findViewById(R.id.txtCarousel);
                    text.setText("Trending Products");
                    ImageView sliderImage = customView.findViewById(R.id.sliderImage);
                    sliderImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            openTrendingIntent(getResources().getString(R.string.most_ordered));
                        }
                    });
                }else if(position == 2){
                    mTxtCarousel.setText(rankings.get(position).getRanking());
                    customView =  getLayoutInflater().inflate(R.layout.row_slider_most_shared, null);
                    TextView text = customView.findViewById(R.id.txtCarousel);
                    text.setText("Trending Products");
                    ImageView sliderImage = customView.findViewById(R.id.sliderImage);
                    sliderImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            openTrendingIntent(getResources().getString(R.string.most_shared));
                        }
                    });
                }
                return customView;
            }
        });
        mCarouselView.setPageCount(rankings.size());
    }
}
