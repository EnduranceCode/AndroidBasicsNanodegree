package com.endurancecode.NewsAppStageTwo;

import android.text.TextUtils;
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
import java.util.List;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * Helper methods related to requesting and receiving news data from The Guardian API.
 */
public final class FetchUtils {

    /* Set the log tag */
    private static final String LOG_TAG = FetchUtils.class.getSimpleName();

    /* Set HTTP connection request timeouts */
    private static final int READ_TIMEOUT = 10000;
    private static final int CONNECT_TIMEOUT = 15000;

    /* Set JSON keys */
    private static final String KEY_RESPONSE = "response";
    private static final String KEY_RESULTS = "results";
    private static final String KEY_SECTION_NAME = "sectionName";
    private static final String KEY_WEB_PUBLICATION_DATE = "webPublicationDate";
    private static final String KEY_WEB_TITLE = "webTitle";
    private static final String KEY_WEB_URL = "webUrl";
    private static final String KEY_FIELDS = "fields";
    private static final String KEY_BYLINE = "byline";

    /**
     * Create a private constructor because no one should ever create a {@link FetchUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name FetchUtils (and an object instance of FetchUtils is not needed).
     */
    private FetchUtils() {
    }

    /**
     * Query the The Guardian API and return a list of {@link News} objects.
     *
     * @param requestUrl url to request, stored as a string
     */
    public static List<News> fetchNewsData(String requestUrl) {

        /* Create a URL object to query */
        URL urlObject = createUrl(requestUrl);

        /* Perform HTTP request to the URL and receive a JSON response back */
        String jsonResponse = null;
        try {
            jsonResponse = httpConnectionRequest(urlObject);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        /* Extract relevant fields from the JSON response, create a list of {@link Earthquake}
         * objects and then return it
         */
        return extractNews(jsonResponse);
    }

    /**
     * Returns new URL object from the given string URL.
     *
     * @param urlString string that represents an url
     */
    private static URL createUrl(String urlString) {
        URL urlObject = null;
        try {
            urlObject = new URL(urlString);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error when creating URL", e);
        }
        return urlObject;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     *
     * @param urlObject url object to connect
     */
    private static String httpConnectionRequest(URL urlObject) throws IOException {

        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        /* We will return an empty string if the url is null */
        if (urlObject == null) {
            return jsonResponse;
        }

        try {
            urlConnection = (HttpURLConnection) urlObject.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(READ_TIMEOUT);
            urlConnection.setConnectTimeout(CONNECT_TIMEOUT);
            urlConnection.connect();

            /*
             * If the request was successful (response code 200) then
             * read the input stream and parse the response
             */
            if (urlConnection.getResponseCode() == HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                /* If the request isn't successful we will log the response code  */
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            /* We handle the IOException */
            Log.e(LOG_TAG, "Problem retrieving the JSON result", e);
        } finally {
            /* Disconnect the network connection */
            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            /* Close the input stream */
            if (inputStream != null) {
                inputStream.close();
            }
        }

        /* Return the response received from the HTTP connection request */
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     *
     * @param inputStream input stream returned by HTTP request
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
        }
        return stringBuilder.toString();
    }

    private static List<News> extractNews(String jsonRoot) {

        /* If the JSON string is empty or null, then return early. */
        if (TextUtils.isEmpty(jsonRoot)) {
            return null;
        }

        /* Create an empty List that we can start adding earthquakes to */
        List<News> news = new ArrayList<>();

        /*
         * Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
         * is formatted, a JSONException exception object will be thrown.
         * Catch the exception so the app doesn't crash, and print the error message to the logs.
         */
        try {
            /* Parse the url JSON response to an JSONObject */
            JSONObject jsonObject = new JSONObject(jsonRoot);

            /*
             * Get the JSONObject that contains the "response" object. This object contains
             * all the data returned by the server
             */
            JSONObject jsonResponseObject = jsonObject.optJSONObject(KEY_RESPONSE);

            /*
             * Get the JSONArray that contains the "results" objects. Those objects represent the
             * news whose data we want to display
             */
            JSONArray jsonResultsArray = jsonResponseObject.optJSONArray(KEY_RESULTS);

            /* Transverse the jsonFeaturesArray to get the data from each earthquake ("feature"),
             * build an {@link Earthquake} object for each earthquake with that data and then
             * add it to ArrayList<Earthquake>
             */
            for (int index = 0; index < jsonResultsArray.length(); index++) {

                /* Get the object inside the jsonResultsArray */
                JSONObject jsonResultsObject = jsonResultsArray.getJSONObject(index);

                /* Get the data to instantiate a new {@link News} object */
                String sectionName = jsonResultsObject.getString(KEY_SECTION_NAME);
                String webPublicationDate = jsonResultsObject.getString(KEY_WEB_PUBLICATION_DATE);
                String webTitle = jsonResultsObject.getString(KEY_WEB_TITLE);
                String webUrl = jsonResultsObject.getString(KEY_WEB_URL);
                String byline = null;
                if (jsonResultsObject.has(KEY_FIELDS)) {
                    byline = jsonResultsObject.optJSONObject(KEY_FIELDS).getString(KEY_BYLINE);
                }

                /* Add the new {@link News} object to the list News */
                news.add(new News(sectionName, webPublicationDate, webTitle, webUrl, byline));
            }
        } catch (JSONException e) {
            /*
             * If an error is thrown when executing any of the above statements in the "try" block,
             * catch the exception here, so the app doesn't crash. Print a log message
             * with the message from the exception.
             */
            Log.e(LOG_TAG, "Problem parsing the jsonRoot returned by the server", e);
        }

        /* Return the list of news */
        return news;
    }
}
