package com.venkat.articles.carousel;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.venkat.articles.R;
import com.venkat.articles.asynctasks.FetchContentTask;
import com.venkat.articles.beans.DataModel;
import com.venkat.articles.listeners.DataParseCallback;
import com.venkat.articles.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class CarouselActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener,DataParseCallback {

    RecyclerView recyclerView;
    private ProgressDialog mProgressDialog;
    private List<DataModel> mDataSet = new ArrayList<>();
    private FetchContentTask mFetchContentTask;
    private int mPageOffset = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carousel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.listView);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        startDataFetch();

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
    private void showNoConnectionToast() {
        Toast.makeText(CarouselActivity.this, R.string.network_disconnect, Toast.LENGTH_SHORT).show();
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
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
//                final List<DataModel> filteredDataList = Util.filterDataList(mDataSet, newText);
//                if (filteredDataList != null) {
//                    adapter.updateListOnSearch(filteredDataList);
//                    mRecyclerView.scrollToPosition(0);
//                }
                return false;
            }
        });

        searchView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {

            @Override
            public void onViewDetachedFromWindow(View arg0) {
                // search was detached/closed
//                adapter.setModels(mDataSet);
//                adapter.notifyDataSetChanged();
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
//                if (isAscending) {
//                    item.setIcon(R.drawable.ic_sort_white_24dp);
//                    mDataSet = Util.sortDescending(mDataSet);
//                    adapter.setModels(mDataSet);
//                } else {
//                    mDataSet = Util.sortAscending(mDataSet);
//                    item.setIcon(R.drawable.ic_sort_white_desc_24dp);
//                    adapter.setModels(mDataSet);
//                }
//                adapter.notifyDataSetChanged();
//                isAscending = !isAscending;
                break;

            default:
                break;
        }

        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
        RecyclerView.Adapter adapter = new RecyclerAdapter(mDataSet);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onTaskFailed(String msg) {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
