package com.endurancecode.NewsAppStageTwo;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<News>>, SharedPreferences.OnSharedPreferenceChangeListener {

    /* Tag for log messages */
    private static final String LOG_TAG = MainActivity.class.getName();

    /* Query URL to request */
    private static final String QUERY_URL = "https://content.guardianapis.com/search?";

    /*
     * Constant value for the news loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int NEWS_LOADER_ID = 1;

    /* We set the empty state TextView as global variable, so we can refer to it in a later method */
    private TextView emptyStateTextView;

    /*
     * To access and modify the instance of the NewsAdapter from the onPostExecute() method,
     * we need to make it a global variable in the MainActivity.
     */
    private NewsAdapter newsAdapter;

    /* Get a reference to the LoaderManager in order to interact with the loaders */
    private LoaderManager loaderManager = getLoaderManager();

    /* Variable to store the reference to the SharedPreferences file */
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Find a reference to the {@link ListView} in the main activity layout */
        ListView newsListView = findViewById(R.id.news_list);

        /* Set an empty view if the news adapter has no data to populate */
        emptyStateTextView = findViewById(R.id.empty_list);
        newsListView.setEmptyView(emptyStateTextView);

        /*
         * Create a new {@Link NewsAdapter} that takes an empty ArrayList as input that will be
         * later updated by the onLoadFinished() method
         */
        newsAdapter = new NewsAdapter(this, new ArrayList<News>());

        /*
         * Set the adapter on the {@Link ListView}
         * so that the list can be populated in the user interface
         */
        newsListView.setAdapter(newsAdapter);

        /* Obtain a reference to the SharedPreferences file for this app */
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        /*
         * And register to be notified of preference changes
         * So we know when the user has adjusted the query settings
         */
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        /* Set an OnItemClickListener on the ListView items */
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /* Get the selected {@Link News} object */
                News selectedNews = newsAdapter.getItem(position);

                /* Set and start a wen browser intent with the news web url */
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(selectedNews.getWebUrl()));
                startActivity(browserIntent);
            }
        });

        /* Get connectivity information status */
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        //noinspection deprecation
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {

            /*
             * We will initialize the {@Link NewsLoader} if there is an active network connection.
             * Pass in the int ID constant defined above and pass in null for the bundle.
             * Pass in this activity for the LoaderCallbacks parameter (which is valid
             * because this activity implements the LoaderCallbacks interface).
             */
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            /*
             * If there is no active network connection we won't initiate the loader and
             * we will hide the loading spinner and we will display a message stating that
             * there is no available network connection
             */

            /* Hide the loading spinner */
            hideLoadingSpinner();

            /* Display a message stating that there is no available network connection */
            emptyStateTextView.setText(R.string.no_internet);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        /* Set up a listener whenever a key changes */
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        /* Unregister the listener whenever a key changes */
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    /* This method is called whenever a preference is changed */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(R.string.settings_page_size_key) || key.equals(R.string.settings_order_by_key)) {
            /* Clear the ListView because a new query will be kicked off */
            newsAdapter.clear();

            /* Hide the empty state text view because the loading indicator will be displayed */
            emptyStateTextView.setVisibility(View.GONE);

            /* Show the loading indicator while new data is being fetched */
            ProgressBar loadingSpinner = findViewById(R.id.loading_spinner);
            loadingSpinner.setVisibility(View.VISIBLE);

            /* Restart the loader to requery the server as the query settings have been updated */
            getLoaderManager().restartLoader(NEWS_LOADER_ID, null, this);
        }
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        /*
         * getString retrieves a String value from the preferences.
         * The second parameter is the default value for this preference.
         */
        String pageSize = sharedPreferences.getString(
                getString(R.string.settings_page_size_key),
                getString(R.string.settings_page_size_default)
        );

        String orderBy = sharedPreferences.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );

        /*
         * The server's number of results limit is 200
         * so we check if the input settings is bigger than 200
         * and if it is we change it to 200
         */
        int pageSizeInteger = Integer.valueOf(pageSize);
        if (pageSizeInteger > 200) {
            pageSize = "200";
        }

        /* Parse breaks apart the URI string that's passed into its parameter */
        Uri baseUri = Uri.parse(QUERY_URL);

        /* buildUpon() prepares the baseUri that we just parsed so we can add query parameters to it */
        Uri.Builder uriBuilder = baseUri.buildUpon();

        /* Append query parameter and its value. For example, the `q=triathlon` */
        uriBuilder.appendQueryParameter("q", "triathlon");
        uriBuilder.appendQueryParameter("type", "article");
        uriBuilder.appendQueryParameter("use-date", "published");
        uriBuilder.appendQueryParameter("show-fields", "byline");
        uriBuilder.appendQueryParameter("page-size", pageSize);
        uriBuilder.appendQueryParameter("order-by", orderBy);
        uriBuilder.appendQueryParameter("api-key", "test");

        /* Log the query URL */
        Log.e(LOG_TAG, "The query URL is " + uriBuilder.toString());

        /*
         * Return the completed URI
         * https://content.guardianapis.com/search?&q=triathlon&type=article&use-date=published&show-fields=byline&page-size=pageSize&order-by=orderBy&api-key=test
         */
        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        /* Clear the adapter of previous earthquake data */
        newsAdapter.clear();

        /* Hide the loading spinner */
        hideLoadingSpinner();

        /* Set empty state text to display "No earthquakes found." */
        emptyStateTextView.setText(R.string.no_news);

        /*
         * If there is a valid list of {@link News} objects add it to the adapter's
         * data set. This will trigger the ListView to update.
         */
        if (news != null && !news.isEmpty()) {
            newsAdapter.addAll(news);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        /* Loader reset, so we can clear out our existing data */
        newsAdapter.clear();
    }

    /* We avoid code repetitions by setting this helper method that hides the loading spinner */
    private void hideLoadingSpinner() {
        ProgressBar loadingSpinner = findViewById(R.id.loading_spinner);
        loadingSpinner.setVisibility(View.GONE);
    }

    /* This method initialize the contents of the Activity's options menu */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Inflate the Options Menu we specified in XML */
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /* This method is called whenever an item in the options menu is selected */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
