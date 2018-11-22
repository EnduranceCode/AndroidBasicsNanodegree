package com.endurancecode.tourguide;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PlaceAdapter extends ArrayAdapter {
    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context The current context. Used to inflate the layout file.
     * @param places  A List of AndroidFlavor objects to display in a list
     */
    public PlaceAdapter(Activity context, ArrayList<Place> places) {
        /* Here, we initialize the ArrayAdapter's internal storage for the context and the list.
         * the second argument is used when the ArrayAdapter is populating a single TextView.
         * Because this is a custom adapter, the adapter is not going to use this second argument,
         * so it can be any value. Here, we used 0.
         */
        super(context, 0, places);
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_card, parent, false);
        }

        /* Get the {@link Place} object located at this position in the list */
        /* TODO: Check why we have to cast getItem(position) */
        Place currentPlace = (Place) getItem(position);

        /* Find the views on the list_item_card.xml layout
         */
        /* Find the ImageView for the card image on the list_item_card.xml layout */
        ImageView imageView = (ImageView) convertView.findViewById(R.id.list_item_card_image);

        /* Find the TextView for the card title in the list_item_card.xml layout */
        TextView titleTextView = (TextView) convertView.findViewById(R.id.list_item_card_title);

        /* Find the TextView for the card subtitle in the list_item_card.xml layout */
        TextView subTitleTextView = (TextView) convertView.findViewById(R.id.list_item_card_subtitle);

        /* Set the current Place object data on the views of the list_item_card.xml layout
         */
        /* Get the image from the current Place object and set it on the ImageView of list_item_card.xml layout */
        imageView.setImageResource(currentPlace.getCardImageResourceID());

        /* Get the title from the current Place object and set it on the title TextView of list_item_card.xml layout */
        titleTextView.setText(currentPlace.getTitle());

        if (currentPlace.getSubtitle() != null) {
            subTitleTextView.setText(currentPlace.getSubtitle());
            subTitleTextView.setVisibility((View.VISIBLE));
        } else {
            subTitleTextView.setVisibility(View.GONE);
        }

        /* Return the whole list item layout so that it can be shown in the ListView */
        return convertView;
    }
}
