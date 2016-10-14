package com.venkat.articles;

import android.app.Application;

import com.venkat.articles.utils.ImageUtil;


public class ArticlesDemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //intialise the Fresco : image lazy loading
        ImageUtil.initFresco(this);
    }
}
