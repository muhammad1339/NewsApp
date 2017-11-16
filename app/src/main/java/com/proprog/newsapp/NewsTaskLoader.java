package com.proprog.newsapp;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;

/**
 * Created by mohamedAHMED on 2017-11-16.
 */

public class NewsTaskLoader extends AsyncTaskLoader<ArrayList<News>> {
    String mUrl;

    public NewsTaskLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<News> loadInBackground() {
        QueryUtils queryUtils = new QueryUtils();
        ArrayList<News> newses = queryUtils.fetchBookData(mUrl);
        return newses;
    }
}
