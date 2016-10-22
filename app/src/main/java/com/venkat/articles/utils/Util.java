package com.venkat.articles.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;

import com.venkat.articles.beans.DataModel;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public class Util {
    /**
     * this method is used to get bit from the raw resource
     *
     * @param ctx is used to get the resource
     * @param res is the raw resource
     * @return
     */
    public static Bitmap getImageBitmapFromRaw(Context ctx, int res) {
        InputStream bm = ctx.getResources().openRawResource(res);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(bm);
        Bitmap bmp = BitmapFactory.decodeStream(bufferedInputStream);
        return bmp;
    }

    /**
     * this method is used to get current time of the device
     *
     * @return
     */
    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    public static List<DataModel> cleanAuthorTitle(List<DataModel> results) {
        Iterator<DataModel> iterator = results.iterator();
        while (iterator.hasNext()) {
            DataModel tempResult = iterator.next();
//            if (tempResult.getmByLine().startsWith("By ")) {
//                tempResult.setmByLine(tempResult.getmByLine().substring(2));
//            }
            if (tempResult.getmTitle()!=null) {
                tempResult.setmTitle(tempResult.getmTitle());
            }

        }
        return results;
    }

    public static List<DataModel> sortAscending(List<DataModel> results) {
        List<DataModel> listToSort = new ArrayList<>(results);
        Collections.sort(listToSort);
        return listToSort;
    }

    public static List<DataModel> sortDescending(List<DataModel> results) {
        List<DataModel> listToReverse = new ArrayList<>(results);
        Collections.reverse(listToReverse);
        return listToReverse;
    }

    public static List<DataModel> filterDataList(List<DataModel> dataSet, String queryString) {
        if (queryString != null && !queryString.equals(AppConstants.EMPTY_STRING)) {
            queryString = queryString.toLowerCase();

            final List<DataModel> filteredDataList = new ArrayList<>();
            for (DataModel result : dataSet) {
                //final String text = result.getmByLine().toLowerCase();
                final String text = result.getmTitle().toLowerCase();
                if (text.contains(queryString)) {
                    filteredDataList.add(result);
                }
            }
            return filteredDataList;
        }
        return null;
    }

    public static void addSwipeRefresh(final SwipeRefreshLayout swipeRefresh, final SwipeRefreshLayout.OnRefreshListener listener) {
        swipeRefresh.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.YELLOW, Color.CYAN);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (listener != null)
                    listener.onRefresh();
            }
        });
    }

    public static boolean isDataNetworkConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }
}
