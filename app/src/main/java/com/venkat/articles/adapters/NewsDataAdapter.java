package com.venkat.articles.adapters;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.venkat.articles.ArticlesActivity;
import com.venkat.articles.R;
import com.venkat.articles.beans.DataModel;

import java.util.ArrayList;
import java.util.List;


public class NewsDataAdapter extends RecyclerView.Adapter<NewsDataAdapter.DataObjectHolder> {

    public interface PaginationRequestCallback {
        public void startLoading();
    }

    private PaginationRequestCallback mPaginationRequestCallback;

    private ArticlesActivity mParentActivity;
    List<DataModel> mDataSet;


    public NewsDataAdapter(ArticlesActivity context, List<DataModel> dataSet) {
        mParentActivity = context;
        mDataSet = new ArrayList<>(dataSet);
        mPaginationRequestCallback = (PaginationRequestCallback) context;
    }

    public void setModels(List<DataModel> dataSet) {
        mDataSet = new ArrayList<>(dataSet);
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView mGenereTextView;
        TextView mTitleTextView;
        ImageView mThumbnailImageView;

        public DataObjectHolder(View itemView) {
            super(itemView);
            mGenereTextView = (TextView) itemView.findViewById(R.id.item_genere);
            mTitleTextView = (TextView) itemView.findViewById(R.id.item_title);
            mThumbnailImageView = (ImageView) itemView.findViewById(R.id.item_thumbnail);
        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mParentActivity).inflate(R.layout.list_item, parent, false);
        DataObjectHolder objectHolder = new DataObjectHolder(itemView);
        return objectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        if (mDataSet != null && mDataSet.size() > 0) {

            DataModel dataItem = mDataSet.get(position);
            holder.mGenereTextView.setText(dataItem.getmTitle());
            holder.mTitleTextView.setText(dataItem.getmByLine());
            if (dataItem.getmImageUrl() != null && dataItem.getmImageUrl().size() > 0) {
                holder.mThumbnailImageView.setImageURI(Uri.parse(dataItem.getmImageUrl().get(0)));
            } else {
                holder.mThumbnailImageView.setImageResource(R.drawable.ic_launcher);
            }
            if (mPaginationRequestCallback != null && position == mDataSet.size() - 1) {
                mPaginationRequestCallback.startLoading();
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public DataModel getItem(int position) {
        if (mDataSet != null && position <= mDataSet.size()) {
            return mDataSet.get(position);
        }

        return null;
    }

    public DataModel removeItem(int position) {
        final DataModel model = mDataSet.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, DataModel model) {
        mDataSet.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final DataModel model = mDataSet.remove(fromPosition);
        mDataSet.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    private void removeItemAndAnimate(List<DataModel> newModels) {
        for (int i = mDataSet.size() - 1; i >= 0; i--) {
            final DataModel model = mDataSet.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void addItemAndAnimate(List<DataModel> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final DataModel model = newModels.get(i);
            if (!mDataSet.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void moveItemAndAnimate(List<DataModel> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final DataModel model = newModels.get(toPosition);
            final int fromPosition = mDataSet.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public void updateListOnSearch(List<DataModel> models) {
        removeItemAndAnimate(models);
        addItemAndAnimate(models);
        moveItemAndAnimate(models);
    }
}
