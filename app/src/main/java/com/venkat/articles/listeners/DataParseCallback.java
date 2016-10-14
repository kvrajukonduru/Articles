package com.venkat.articles.listeners;

import com.venkat.articles.beans.DataModel;

import java.util.List;


public interface DataParseCallback {
    public void onDataParsed(List<DataModel> result);

    public void onTaskFailed(String msg);
}
