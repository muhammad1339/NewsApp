package com.proprog.newsapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by mohamedAHMED on 2017-11-16.
 */

public class QueryUtils {
    private final String LOG_MSG_CLASS = QueryUtils.class.getSimpleName();
    public static boolean IS_URL_GOOD = true;

    public QueryUtils() {
    }



    public ArrayList<News> fetchBookData(String queryUrl) {
        //prepare url
        URL url = createURL(queryUrl);
        //fetch json data from url
        String jsonResponse = makeHttpRequest(url);
        ArrayList<News> newses ;
        newses = extractBookFeature(jsonResponse);
        return newses;
    }

    public ArrayList<News> extractBookFeature(String jsonResponse) {
        News news;
        ArrayList<News> newses = new ArrayList<>();
        JSONObject root;
        try {
            root = new JSONObject(jsonResponse);
            JSONObject response = root.optJSONObject("response");
            JSONArray results = response.optJSONArray("results");
            if (results != null) {
                Log.d(LOG_MSG_CLASS, "extractBookFeature : " + String.valueOf(results.length()));
                for (int i = 0; i < results.length(); i++) {
                    JSONObject result = results.optJSONObject(i);
                    String sectionName = result.optString("sectionName");
                    String webPublicationDate = result.optString("webPublicationDate");
                    String webTitle = result.optString("webTitle");
                    String webUrl = result.optString("webUrl");
                    news = new News(sectionName, webPublicationDate, webTitle, webUrl);
                    newses.add(news);
                }
            } else {
                Log.d(LOG_MSG_CLASS, "No Items");
                IS_URL_GOOD = false;
            }

        } catch (JSONException e) {
            Log.d(LOG_MSG_CLASS, "JSON Exception" + e.getMessage());
            IS_URL_GOOD = false;
        }


        return newses;
    }

    public URL createURL(String urlQuery) {
        URL url = null;
        try {
            url = new URL(urlQuery);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public String makeHttpRequest(URL url) {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                jsonResponse = readFromInputStream(urlConnection.getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //release Resources
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return jsonResponse;
    }

    public String readFromInputStream(InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        BufferedReader bufferReader = new BufferedReader(inputStreamReader);
        try {
            String line = bufferReader.readLine();
            while (line != null) {
                builder.append(line);
                line = bufferReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }


}
