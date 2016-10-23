package com.venkat.articles.carousel;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.venkat.articles.R;
import com.venkat.articles.beans.DataModel;

import java.util.List;

import me.relex.circleindicator.CircleIndicator;

class RecyclerAdapter extends RecyclerView.Adapter {
    com.venkat.articles.WrapContentHeightViewPager mPager;
    CircleIndicator mCircleIndicator;
    ViewPagerAdapter adapterCarousel;
    private List<DataModel> mDataSet;

    public RecyclerAdapter(List<DataModel> data) {
        mDataSet = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.carousel_pager_row_item, null);
        mPager = (com.venkat.articles.WrapContentHeightViewPager) v.findViewById(R.id.pager);
        adapterCarousel = new ViewPagerAdapter(mDataSet);
        mCircleIndicator = (CircleIndicator) v.findViewById(R.id.indicator);
        mPager.setAdapter(adapterCarousel);
        mCircleIndicator.setViewPager(mPager);
        adapterCarousel.registerDataSetObserver(mCircleIndicator.getDataSetObserver());
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPager.invalidate();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if (mDataSet.size()>10)
        return 10;
        else
            return mDataSet.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}