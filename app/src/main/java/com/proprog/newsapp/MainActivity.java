package com.proprog.newsapp;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import static android.R.id.empty;

public class MainActivity
        extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<ArrayList<News>> {
    ArrayList<News> newses;
    RecyclerView recyclerView;
    NewsListAdapter newsListAdapter;
    private final static int LOADER_ID = 0;
    private final static int NO_DATA_ID = 1;
    private final static int NO_NETWORK_ID = 2;
    private final static int GOOD_ID = 3;
    private final static int LOADING_ID = 4;

    private View empty;
    private View disconnected;
    private View loading;

    private String fullUrl = "http://content.guardianapis.com/search?q=debates&api-key=test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        disconnected = findViewById(R.id.no_internet_state_view);

        loading = findViewById(R.id.loading);

        if (!isConnectedToInternet()) {
            handleStates(NO_NETWORK_ID);
            Log.d("Newa", "No");
        } else {
            getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        }
    }

    @Override
    public Loader<ArrayList<News>> onCreateLoader(int id, Bundle args) {
        handleStates(LOADING_ID);
        return new NewsTaskLoader(getApplicationContext(), fullUrl);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<News>> loader, ArrayList<News> data) {

        handleStates(GOOD_ID);
        newses = new ArrayList<>();
        newses.addAll(data);
        for (News news : newses) {
            Log.d("News", news.toString());
        }
        newsListAdapter = new NewsListAdapter(getApplicationContext(), data);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(newsListAdapter);

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<News>> loader) {
    }

    public void handleStates(int stateId) {
        switch (stateId) {
            case NO_NETWORK_ID:
                recyclerView.setVisibility(View.GONE);
                disconnected.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);
                break;
            case LOADING_ID:
                recyclerView.setVisibility(View.GONE);
                disconnected.setVisibility(View.GONE);
                loading.setVisibility(View.VISIBLE);
                break;
            default:
                recyclerView.setVisibility(View.VISIBLE);
                disconnected.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
                break;
        }
    }

    public boolean isConnectedToInternet() {
        boolean netState = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            netState = true;
        }
        return netState;
    }
}
