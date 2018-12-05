package com.endurancecode.quakereport;

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

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {
    /* Set log tag */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Query the USGS dataset and return a list of {@link Earthquake} objects.
     *
     * @param requestUrl url to request stored as a string
     */
    public static List<Earthquake> fetchEarthQuakeData(String requestUrl) {

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
        return extractEarthquakes(jsonResponse);
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
            Log.e(LOG_TAG, "Error when creting URL", e);
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

        /* We will return an empty string if url is null */
        if (urlObject == null) {
            return jsonResponse;
        }

        try {
            urlConnection = (HttpURLConnection) urlObject.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            /*
             * If the request was successful (response code 200) then
             * read the input stream and parse the response
             */
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                /* If the request isn't successful we will log the response code  */
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            /* We handles the IOException */
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

    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing the given JSON response.
     *
     * @param jsonResponse response returned by HTTP request
     */
    private static List<Earthquake> extractEarthquakes(String jsonResponse) {

        /* If the JSON string is empty or null, then return early. */
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        /* Create an empty List that we can start adding earthquakes to */
        List<Earthquake> earthquakes = new ArrayList<>();

        /*
         * Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
         * is formatted, a JSONException exception object will be thrown.
         * Catch the exception so the app doesn't crash, and print the error message to the logs.
         */
        try {
            /* Parse the url JSON response to an JSONObject */
            JSONObject jsonRootObject = new JSONObject(jsonResponse);

            /*
             * Get the JSONArray that contains the "features" objects. Those objects represent the
             * earthquakes whose data we want to display
             */
            JSONArray jsonFeaturesArray = jsonRootObject.optJSONArray("features");

            /* Transverse the jsonFeaturesArray to get the data from each earthquake ("feature"),
             * build an {@link Earthquake} object for each earthquake with that data and then
             * add it to ArrayList<Earthquake>
             */
            for (int index = 0; index < jsonFeaturesArray.length(); index++) {
                JSONObject jsonFeatureObject = jsonFeaturesArray.getJSONObject(index);

                JSONObject jsonPropertiesObject = jsonFeatureObject.getJSONObject("properties");

                double mag = jsonPropertiesObject.getDouble("mag");
                String place = jsonPropertiesObject.getString("place");
                long time = jsonPropertiesObject.getLong("time");
                String url = jsonPropertiesObject.getString("url");

                earthquakes.add(new Earthquake(mag, place, time, url));
            }

        } catch (JSONException e) {
            /*
             * If an error is thrown when executing any of the above statements in the "try" block,
             * catch the exception here, so the app doesn't crash. Print a log message
             * with the message from the exception.
             */
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        /* Return the list of earthquakes */
        return earthquakes;
    }
}
