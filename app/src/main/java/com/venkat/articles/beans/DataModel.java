package com.venkat.articles.beans;

import java.util.ArrayList;


public class DataModel implements Comparable<DataModel> {
    String mByLine;
    String mTitle;
    ArrayList<String> mImageUrls;
    String  mItemImageUrl;
    String  mItemCost;
    String mManfacturer;
    String mItemName;

    public DataModel() {

    }


    public DataModel( String title, String imageUrl,String price,String manufacturer,String itemname) {
        mItemCost =price;
        mTitle = title;
        mItemImageUrl = imageUrl;
        mManfacturer =manufacturer;
        mItemName = itemname;

    }

//    public String getmByLine() {
//        return mByLine;
//    }
//
//    public void setmByLine(String mByLine) {
//        this.mByLine = mByLine;
//    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getItemImageURL() {
        return mItemImageUrl;
    }

    public void setItemImageURL(String aUrl) {
        this.mItemImageUrl = aUrl;
    }

    public String getItemManfacturer() {
        return mManfacturer;
    }

    public void setItemManfacturer(String aManfacturer) {
        this.mManfacturer = aManfacturer;
    }


    public String getItemname() {
        return mItemName;
    }

    public void setItemName(String aName) {
        this.mItemName = aName;
    }

    public String getItemCost() {
        return mItemCost;
    }

    public void setItemCost(String aPrice) {
        this.mItemCost = aPrice;
    }



    @Override
    public int compareTo(DataModel dataModel) {

        //return this.getmByLine().compareTo(dataModel.getmByLine());
        return this.getmTitle().compareTo(dataModel.getmTitle());
    }

}
