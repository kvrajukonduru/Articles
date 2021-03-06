package com.venkat.articles.carousel;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.venkat.articles.R;
import com.venkat.articles.beans.DataModel;

import java.util.List;

public class ViewPagerAdapter extends android.support.v4.view.PagerAdapter {

    final int NumberOfPages = 5;
    private List<DataModel> mDataSet;

    public ViewPagerAdapter(List<DataModel> data) {
        mDataSet = data;
    }

    @Override
    public int getCount() {
        if (mDataSet.size() > NumberOfPages)
            return NumberOfPages;
        return mDataSet.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (LinearLayout) object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        View v = LayoutInflater.from(container.getContext()).inflate(R.layout.carousel_list_item_row, container, false);
        final SimpleDraweeView dv1 = (SimpleDraweeView) v.findViewById(R.id.imageView1);
        final SimpleDraweeView dv2 = (SimpleDraweeView) v.findViewById(R.id.imageView2);
        TextView t1 = (TextView) v.findViewById(R.id.tv11);
        TextView t2 = (TextView) v.findViewById(R.id.tv12);

        TextView t21 = (TextView) v.findViewById(R.id.tv21);
        TextView t22 = (TextView) v.findViewById(R.id.tv22);

        final DataModel dm = mDataSet.get(position);
        final DataModel dm2 = mDataSet.get(position+1);

        if (dm.getItemImageURL() != null) {
            dv1.setImageURI(Uri.parse(dm.getItemImageURL()));
            dv2.setImageURI(Uri.parse(dm2.getItemImageURL()));
        } else {
            dv2.setImageResource(R.drawable.ic_launcher);
            dv1.setImageResource(R.drawable.ic_launcher);
        }

        t2.setText(dm.getmTitle());
        t1.setText(dm.getItemname());

        t21.setText(dm2.getmTitle());
        t22.setText(dm2.getItemname());

        dv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductDetailsActivity.newIntent(container.getContext(), dm.getItemImageURL(), dm.getmTitle(), dm.getItemname());
            }
        });
        dv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductDetailsActivity.newIntent(container.getContext(), dm2.getItemImageURL(), dm2.getmTitle(), dm2.getItemname());

            }
        });

        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
    public int getItemPosition(Object object){
        return POSITION_NONE;
    }
}