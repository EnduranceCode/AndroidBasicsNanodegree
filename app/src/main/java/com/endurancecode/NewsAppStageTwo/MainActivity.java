package com.endurancecode.NewsAppStageTwo;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<News>> {

    /* Tag for log messages */
    private static final String LOG_TAG = MainActivity.class.getName();

    /* Query URL to request */
    private static final String QUERY_URL = "https://content.guardianapis.com/search?&q=triathlon&type=article&use-date=published&order-by=newest&show-fields=byline&page-size=20&api-key=test";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Find a reference to the {@link ListView} in the main activity layout */
        ListView newsListView = findViewById(R.id.news_list);

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

        /* Set an empty view if the news adapter has no data to populate */
        emptyStateTextView = findViewById(R.id.empty_list);
        newsListView.setEmptyView(emptyStateTextView);

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
             * We will initiate the {@Link NewsLoader} if there is an active network connection
             */

            /* Get a reference to the LoaderManager in order to interact with the loaders */
            LoaderManager loaderManager = getLoaderManager();

            /*
             * Initialize the loader. Pass in the int ID constant defined above and pass in null for
             * the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
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
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(this, QUERY_URL);
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
}
