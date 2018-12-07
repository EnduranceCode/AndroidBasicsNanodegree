package com.endurancecode.NewsAppStageOne;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

/**
 * Loads a list of news by using an AsyncTask to perform the
 * network request to the given URL.
 */
@SuppressWarnings("ALL")
public class NewsLoader extends AsyncTaskLoader<List<News>> {

    /* Tag for log messages */
    private static final String LOG_TAG = NewsLoader.class.getName();

    /* Declares the variable to store the url to request */
    private String urlRequest;

    /**
     * Constructs a new {@link News}.
     *
     * @param context of the activity
     * @param url     to load data from
     */
    public NewsLoader(@NonNull Context context, String url) {
        super(context);

        urlRequest = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<News> loadInBackground() {

        /* Don't perform the request if URL is null. */
        if (urlRequest == null) {
            return null;
        }

        /* Perform the network request, parse the response, and extract a list of earthquakes. */
        return FetchUtils.fetchNewsData(urlRequest);
    }
}
