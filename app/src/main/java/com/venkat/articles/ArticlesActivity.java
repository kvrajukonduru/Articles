package com.venkat.articles;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.venkat.articles.adapters.NewsDataAdapter;
import com.venkat.articles.asynctasks.FetchContentTask;
import com.venkat.articles.beans.DataModel;
import com.venkat.articles.listeners.DataParseCallback;
import com.venkat.articles.listeners.RecyclerViewItemClickListener;
import com.venkat.articles.utils.AppConstants;
import com.venkat.articles.utils.Util;

import java.util.ArrayList;
import java.util.List;

/*
   ArticlesActivity.java class is responsible to fetch the list of articles using webservice.
   Details : Article image,Title and overview of the article.
   Features : 1) List of Articles Ascending/descending order
              2) Search  list of Articles by Name
 */

public class ArticlesActivity extends AppCompatActivity implements DataParseCallback, NewsDataAdapter.PaginationRequestCallback {

    private List<DataModel> mDataSet = new ArrayList<>();
    private NewsDataAdapter adapter;
    private ProgressDialog mProgressDialog;
    private boolean isAscending = true;
    private RecyclerView mRecyclerView;
    private FetchContentTask mFetchContentTask;

    private int mPageOffset = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_title));
        toolbar.setSubtitle(getResources().getString(R.string.app_subtitle));
        setSupportActionBar(toolbar);


        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NewsDataAdapter(this, mDataSet);
        mRecyclerView.setAdapter(adapter);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerViewItemClickListener(ArticlesActivity.this, new RecyclerViewItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Intent detailActivityIntent = new Intent(ArticlesActivity.this, ArticleDetailsActivity.class);
                        DataModel itemClicked = adapter.getItem(position);
                        String mediaMetadataurl = itemClicked.getItemImageURL();


                        detailActivityIntent.putExtra(AppConstants.INTENT_EXTRA_KEY_IMAGE_URL, mediaMetadataurl);
                        detailActivityIntent.putExtra(AppConstants.INTENT_EXTRA_KEY_AUTHOR, itemClicked.getItemManfacturer());
                        detailActivityIntent.putExtra(AppConstants.INTENT_EXTRA_KEY_TITLE, itemClicked.getmTitle());
                        startActivity(detailActivityIntent);
                    }
                })
        );

        startDataFetch();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mFetchContentTask != null) {
            mFetchContentTask.cancel(true);
        }
    }

    private void startDataFetch() {
        if (Util.isDataNetworkConnected(this)) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.fetch_articles));
            mProgressDialog.show();
            mFetchContentTask = new FetchContentTask(this);

            mFetchContentTask.execute(getPageOffset() + ".json");
        } else {
            showNoConnectionToast();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Log.d("SWAT", "onQueryTextChange ");
                final List<DataModel> filteredDataList = Util.filterDataList(mDataSet, newText);
                if (filteredDataList != null) {
                    adapter.updateListOnSearch(filteredDataList);
                    mRecyclerView.scrollToPosition(0);
                }
                return false;
            }
        });

        searchView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {

            @Override
            public void onViewDetachedFromWindow(View arg0) {
                // search was detached/closed
                adapter.setModels(mDataSet);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onViewAttachedToWindow(View arg0) {
                // search was opened
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort:
                if (isAscending) {
                    item.setIcon(R.drawable.ic_sort_white_24dp);
                    mDataSet = Util.sortDescending(mDataSet);
                    adapter.setModels(mDataSet);
                } else {
                    mDataSet = Util.sortAscending(mDataSet);
                    item.setIcon(R.drawable.ic_sort_white_desc_24dp);
                    adapter.setModels(mDataSet);
                }
                adapter.notifyDataSetChanged();
                isAscending = !isAscending;
                break;

            default:
                break;
        }

        return true;
    }

    private void showNoConnectionToast() {
        Toast.makeText(ArticlesActivity.this, R.string.network_disconnect, Toast.LENGTH_SHORT).show();
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onDataParsed(List<DataModel> result) {
        //Log.d("SWAT", "onDataParsed : size :  " + result.size() + ", offset : "+ pageOffset);
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        List<DataModel> cleanedList = Util.cleanAuthorTitle(result);
        mDataSet.addAll(Util.sortAscending(cleanedList));
        adapter.setModels(mDataSet);
        adapter.notifyDataSetChanged();

//        List<DataModel> cleanedList = Util.cleanAuthorTitle(result);
//        mDataSet.addAll(cleanedList);
//        adapter.setModels(mDataSet);
//        adapter.notifyDataSetChanged();
    }

    @Override
    public void onTaskFailed(String msg) {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private int getPageOffset() {
        //rotate the page offset once it reaches to 4
        if (mPageOffset >= 4) {
            mPageOffset = 1;
        } else {
            mPageOffset++;
        }

        return mPageOffset;
    }

    @Override
    public void startLoading() {
        startDataFetch();
        mFetchContentTask = new FetchContentTask(this);
        mFetchContentTask.execute(getPageOffset() + ".json");
    }


}
