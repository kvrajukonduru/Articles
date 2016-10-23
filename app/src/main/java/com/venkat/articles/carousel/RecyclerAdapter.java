package com.venkat.articles.carousel;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.venkat.articles.R;
import com.venkat.articles.beans.DataModel;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

class RecyclerAdapter extends RecyclerView.Adapter {
    ViewPagerAdapter adapterCarousel;
    private List<DataModel> mDataSet;

    public RecyclerAdapter(List<DataModel> data) {
        mDataSet = data;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.carousel_pager_row_item, parent,false);
        ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder viewHolder= (ViewHolder) holder;
        adapterCarousel = new ViewPagerAdapter(mDataSet);
        viewHolder.mPager.setAdapter(adapterCarousel);
        viewHolder.mPager.setOffscreenPageLimit(0);
        viewHolder.mIndicater.setViewPager(viewHolder.mPager);
        viewHolder.mPager.setOffscreenPageLimit(0);
        adapterCarousel.registerDataSetObserver(viewHolder.mIndicater.getDataSetObserver());
        viewHolder.mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public int getItemCount() {
        if (mDataSet.size()>5)
        return 5;
        else
            return mDataSet.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ViewPager mPager;
        CircleIndicator mIndicater;

        public ViewHolder(View itemView) {
            super(itemView);
            mPager = (com.venkat.articles.WrapContentHeightViewPager) itemView.findViewById(R.id.pager);
            mIndicater = (CircleIndicator) itemView.findViewById(R.id.indicator);
        }
    }
}