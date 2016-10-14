package com.venkat.articles.asynctasks;


import android.app.Activity;
import android.os.AsyncTask;

import com.venkat.articles.beans.DataModel;
import com.venkat.articles.listeners.DataParseCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/*
    FetchContentTask.java class is responsible to handle network calls and fetch the data.
      Data will be parsed based on JSON tags and set the  DataModel

 */

public class FetchContentTask extends AsyncTask<String, Void, ArrayList<DataModel>> {
    DataParseCallback mCallback;
    private Activity mParentActivity;


    public FetchContentTask(Activity parent) {
        mParentActivity = parent;
        mCallback = (DataParseCallback) parent;
    }

    @Override
    protected ArrayList<DataModel> doInBackground(String[] urls) {
        ArrayList<DataModel> tempDataSet = new ArrayList<>();
        String jsonPath = urls[0];
        try {

            String jsonData = loadJSONFromAsset(jsonPath);

            if (jsonData != null) {

                JSONObject jsonObject = new JSONObject(jsonData);
                JSONArray newsContents = jsonObject.getJSONArray("results");
                for (int i = 0; i < newsContents.length(); i++) {
                    JSONObject individualContent = newsContents.getJSONObject(i);
                    DataModel dataItem = new DataModel();
                    dataItem.setmByLine(individualContent.getString("byline"));
                    dataItem.setmTitle(individualContent.getString("title"));

                    if (individualContent.get("media") instanceof JSONArray) {
                        JSONArray individualContentMedia = individualContent.getJSONArray("media");
                        if (individualContentMedia != null && individualContentMedia.length() > 0) {
                            for (int j = 0; j < individualContentMedia.length(); j++) {
                                JSONObject mediaItem = individualContentMedia.getJSONObject(j);
                                JSONArray mediaMetaDataArray = mediaItem.getJSONArray("media-metadata");
                                if (mediaMetaDataArray != null && mediaMetaDataArray.length() > 0) {
                                    ArrayList<String> imageUrls = new ArrayList<>();
                                    for (int k = 0; k < mediaMetaDataArray.length(); k++) {
                                        JSONObject metadataItem = mediaMetaDataArray.getJSONObject(k);
                                        imageUrls.add(metadataItem.getString("url"));

                                    }
                                    dataItem.setmImageUrl(imageUrls);
                                }
                            }

                        }
                    }
                    tempDataSet.add(dataItem);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

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

    public String loadJSONFromAsset(String jsonPath) {
        String json = null;
        try {
            InputStream is = mParentActivity.getAssets().open(jsonPath);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

//    private String loadJSONFromServer(String jsonUrl) {
//        String json = null;
//        try {
//            URL url = new URL(jsonUrl);
//
//            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//            InputStream inputStream = urlConnection.getInputStream();
//
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            byte[] data = new byte[1024];
//            int len;
//            while ((len = inputStream.read(data)) > -1) {
//                bos.write(data, 0, len);
//            }
//
//            bos.flush();
//
//            json = new String(bos.toByteArray());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return json;
//    }
}
