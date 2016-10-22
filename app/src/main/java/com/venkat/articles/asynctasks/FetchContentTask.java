package com.venkat.articles.asynctasks;


import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.venkat.articles.beans.DataModel;
import com.venkat.articles.listeners.DataParseCallback;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
    FetchContentTask.java class is responsible to handle network calls and fetch the data.
      Data will be parsed based on JSON tags and set the  DataModel

 */

public class FetchContentTask extends AsyncTask<String, Void, ArrayList<DataModel>> {
    private static final String IMAGE_PATTERN =
            "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)";
    private static String TAG = FetchContentTask.class.getSimpleName();
    private final String URL = "http://www.landmarkshops.com/";
    DataParseCallback mCallback;
    //private final String MEN_TOPS_URL = "http://www.landmarkshops.com/department/men";
    private Activity mParentActivity;
    private Pattern pattern;
    private Matcher matcher;


    public FetchContentTask(Activity parent) {
        pattern = Pattern.compile(IMAGE_PATTERN);
        mParentActivity = parent;
        mCallback = (DataParseCallback) parent;
    }

    @Override
    protected ArrayList<DataModel> doInBackground(String[] urls) {
        ArrayList<DataModel> tempDataSet = new ArrayList<>();

        try {
            // Connect to the WEB  URL
            Document doc = Jsoup.connect(URL).get();

            Elements media = doc.select("[src]");
            //Elements links = doc.select("a[href]");
            //Elements imports = doc.select("link[href]");

            Log.d(TAG, " Media Info:" + media.size());

            for (Element src : media) {
                if (src.tagName().equals("img")) {
                    //Log.i(TAG,"media tag::"+ src.toString());
                    String absUrl = src.attr("abs:src").toString();
                    Log.i(TAG, "ABS URL::" + absUrl);
                    String itemtitle = src.attr("alt");
                    Log.i(TAG, "Item Title::" + itemtitle);
                    if (validate(absUrl) && isItemTitleValid(itemtitle)) {
                        DataModel dataItem = new DataModel();
                        dataItem.setmTitle(src.attr("alt"));
                        dataItem.setItemImageURL(src.attr("abs:src"));
                        dataItem.setItemCost("AED 99 "); //dummy cost info (static data)
                        dataItem.setItemManfacturer("Land Mark "); //dummy manufacturer info (static data)
                        dataItem.setItemName("Sample Item 1 "); //dummy item info (static data)

                        tempDataSet.add(dataItem);
                    } else {
                        Log.d(TAG, "INVALID INPUT URL !!!!" + absUrl);

                    }
                    //System.out.println(" * %s: <%s> %sx%s (%s)" +
                    //      src.tagName() + "," + src.attr("abs:src") + "," + src.attr("width") + "," + src.attr("height") + ","
                    //     + trim(src.attr("alt"), 20));
                    Log.d(TAG, "IMAGE  Abs Url Info ::" + src.attr("alt") + ":" + src.attr("abs:src"));
                } else {
                    //TBD
                    //System.out.println(" raju::: * %s: <%s>" + src.tagName() + "," + src.attr("abs:src"));
                }


            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d(TAG," Product DATE SIZE::"+tempDataSet.size());
        return tempDataSet;
    }

    @Override
    protected void onPostExecute(ArrayList<DataModel> result) {
        if (!isCancelled() && mCallback != null) {
            if (result != null && result.size() > 0) {
                mCallback.onDataParsed(result);
            } else {
                mCallback.onTaskFailed("Data Parse Error");
            }
        }
    }

    /**
     * Validate image with regular expression
     *
     * @param image image for validation
     * @return true valid image, false invalid image
     */
    public boolean validate(final String image) {

        matcher = pattern.matcher(image);
        return matcher.matches();

    }

    boolean isItemTitleValid(String str) {
        return str != null && str.trim().length() > 0;
    }

}
