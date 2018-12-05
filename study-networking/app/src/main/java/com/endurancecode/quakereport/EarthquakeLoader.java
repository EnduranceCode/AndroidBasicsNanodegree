package com.endurancecode.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Loads a list of earthquakes by using an AsyncTask to perform the
 * network request to the given URL.
 */
@SuppressWarnings("deprecation")
public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {
    /**
     * Tag for log messages
     */
    private static final String LOG_TAG = EarthquakeLoader.class.getName();

    /* Declares the variable to store the url to request */
    private String urlRequest;

    /**
     * Constructs a new {@link EarthquakeLoader}.
     *
     * @param context of the activity
     * @param url     to load data from
     */
    public EarthquakeLoader(Context context, String url) {
        super(context);

        urlRequest = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Earthquake> loadInBackground() {
        /* Don't perform the request if URL is null. */
        if (urlRequest == null) {
            return null;
        }

        /* Perform the network request, parse the response, and extract a list of earthquakes. */
        return QueryUtils.fetchEarthQuakeData(urlRequest);
    }
}
