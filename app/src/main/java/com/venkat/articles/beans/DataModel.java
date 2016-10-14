package com.venkat.articles.beans;

import java.util.ArrayList;


public class DataModel implements Comparable<DataModel> {
    String mByLine;
    String mTitle;
    ArrayList<String> mImageUrls;

    public DataModel() {

    }

    public DataModel(String byline, String title, ArrayList<String> imageUrl) {
        mByLine = byline;
        mTitle = title;
        mImageUrls = imageUrl;
    }

    public String getmByLine() {
        return mByLine;
    }

    public void setmByLine(String mByLine) {
        this.mByLine = mByLine;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public ArrayList<String> getmImageUrl() {
        return mImageUrls;
    }

    public void setmImageUrl(ArrayList<String> mImageUrl) {
        this.mImageUrls = mImageUrl;
    }


    @Override
    public int compareTo(DataModel dataModel) {

        return this.getmByLine().compareTo(dataModel.getmByLine());
    }

}
