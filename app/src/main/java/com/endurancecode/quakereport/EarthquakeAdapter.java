package com.endurancecode.quakereport;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * {@link EarthquakeAdapter} is an {@link ArrayAdapter} that can provide the layout for each list
 * based on a data source, which is a list of {@link Earthquake} objects.
 * Since this adapter is used to convert the data source of type Earthquake to a view, we can avoid
 * explicit casting by having the class extend ArrayAdapter<Earthquake>
 */
public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
    /*
     * The part of the location string from the USGS service that we use to determine
     * whether or not there is a location offset present ("5km N of Cairo, Egypt").
     */
    private static final String LOCATION_SEPARATOR = " of ";

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context     The current context. Used to inflate the layout file.
     * @param earthquakes A List of {@link Earthquake} objects to display in a list
     */
    public EarthquakeAdapter(Activity context, ArrayList<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position    The position in the list of data that should be displayed in the
     *                    list item view.
     * @param convertView The recycled view to populate.
     * @param parent      The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        /* Check if the existing view is being reused, otherwise inflate the view */
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_earthquake, parent, false);
        }

        /* Get the {@link Earthquake} object located at this position in the list */
        Earthquake currentEarthquake = getItem(position);

        /* Find the views on the list_item_earthquake.xml layout
         */
        /* Find the TextView for the earthquake magnitude */
        TextView magnitudeTextView = convertView.findViewById(R.id.magnitude);

        /* Find TextView for the earthquake location */
        TextView locationTextView = convertView.findViewById(R.id.location);

        /* Find TextView for the earthquake location offset */
        TextView locationOffsetTextView = convertView.findViewById(R.id.location_offset);

        /* Find the TextView for the earthquake date */
        TextView dateTextView = convertView.findViewById(R.id.formatted_date);

        /* Find the TextView for the earthquake time */
        TextView timeTextView = convertView.findViewById(R.id.formatted_time);

        /*
         * Set the current {@link Earthquake} object data on the TextViews of list_item_earthquake.xml
         */
        /* Get the magnitude from the current {@link Earthquake} object and set it on the adequate TextView */
        magnitudeTextView.setText(Double.toString(currentEarthquake.getMagnitude()));

        /*
         * Get the location from the current {@link Earthquake} object and
         * if the original location string (i.e. "5km N of Cairo, Egypt") contains
         * a primary location (Cairo, Egypt) and a location offset (5km N of that city)
         * then store the primary location separately from the location offset in 2 Strings,
         * so they can be displayed in 2 TextViews.
         */
        String jsonLocation = currentEarthquake.getLocation();
        String location_primary;
        String location_offset;

        /* Check whether the originalLocation string contains the " of " text */
        if (jsonLocation.contains(LOCATION_SEPARATOR)) {
            /*
             * Split the string into different parts (as an array of Strings)
             * based on the " of " text. We expect an array of 2 Strings, where
             * the first String will be "5km N" and the second String will be "Cairo, Egypt".
             */
            String[] locationParts = jsonLocation.split(LOCATION_SEPARATOR);

            /* Location offset should be "5km N " + " of " --> "5km N of" */
            location_offset = locationParts[0] + LOCATION_SEPARATOR;

            /* Primary location should be "Cairo, Egypt" */
            location_primary = locationParts[1];
        } else {
            /*
             * Otherwise, there is no " of " text in the originalLocation string.
             * Hence, set the default location offset to say "Near the".
             */
            location_offset = getContext().getString(R.string.near_the);

            /* The primary location will be the full location string "Pacific-Antarctic Ridge" */
            location_primary = jsonLocation;
        }

        /* Set the offset location on the adequate TextView */
        locationOffsetTextView.setText(location_offset);

        /* Set the location on the adequate TextView */
        locationTextView.setText(location_primary);

        /* Get the time from the current {@link Earthquake} and create a new {@link Date} object with it */
        Date timeDateObject = new Date(currentEarthquake.getTimeMilliseconds());

        /* Format the timeDateObject as a date String and set it on the adequate TextView */
        dateTextView.setText(formatDate(timeDateObject));

        /* Format the timeDateObject as a time String and set it on the adequate TextView */
        timeTextView.setText(formaTime(timeDateObject));

        /* Return the whole list item layout so that it can be shown in the ListView */
        return convertView;
    }

    /**
     * Return the formatted date string (i.e. "Jun 6, 2001") from a {@link Date} object
     *
     * Android documentation on SimpleDateFormat:
     * https://developer.android.com/reference/java/text/SimpleDateFormat
     *
     * @param dateObject {@link Date} object to be formatted
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        return dateFormatter.format(dateObject);
    }

    /**
     * Return the formatted time string (i.e. "3:00 AM") from a {@link Date} object
     *
     * Android documentation on SimpleDateFormat:
     * https://developer.android.com/reference/java/text/SimpleDateFormat
     *
     * @param dateObject {@link Date} object to be formatted
     */
    private String formaTime(Date dateObject) {
        SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
        return timeFormatter.format(dateObject);
    }
}
