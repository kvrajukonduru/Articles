package com.venkat.articles;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import me.relex.circleindicator.CircleIndicator;

public class CarouselActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carousel);
        recyclerView = (RecyclerView) findViewById(R.id.listView);
        RecyclerView.Adapter adapter = new Adapter();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

    }

    class Adapter extends RecyclerView.Adapter {
        com.venkat.articles.WrapContentHeightViewPager mPager;
        CircleIndicator mCircleIndicator;
        MyPagerAdapter adapterCarousel;

        public Adapter() {

        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.carousel_pager_row_item, null);
            mPager = (com.venkat.articles.WrapContentHeightViewPager) v.findViewById(R.id.pager);
            adapterCarousel = new MyPagerAdapter();
            mCircleIndicator = (CircleIndicator) v.findViewById(R.id.indicator);
            mPager.setAdapter(adapterCarousel);
            mCircleIndicator.setViewPager(mPager);
            adapterCarousel.registerDataSetObserver(mCircleIndicator.getDataSetObserver());
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 5;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
    }


    private class MyPagerAdapter extends PagerAdapter {
        int NumberOfPages = 5;

        @Override
        public int getCount() {
            return NumberOfPages;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == (LinearLayout) object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View v = LayoutInflater.from(container.getContext()).inflate(R.layout.carousel_list_item_row, container, false);
            container.addView(v);
            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }

    }


}
