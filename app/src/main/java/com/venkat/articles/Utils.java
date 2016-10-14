package com.venkat.articles;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 */
public class Utils {
    public static float getDensity(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                .getMetrics(displayMetrics);
        return displayMetrics.density;
    }
}
