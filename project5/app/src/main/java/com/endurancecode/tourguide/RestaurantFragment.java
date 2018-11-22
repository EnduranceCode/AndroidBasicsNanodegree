package com.endurancecode.tourguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * {@link Fragment} that displays a list of category park and sport places.
 */
public class RestaurantFragment extends Fragment {
    /**
     * To facilitate the addition of new {@link Place} objects and the edition of existing {@link Place}
     * we will store its parameters in arrays
     */
    /* Handles the number existent {@link Place} objects that belong to the restaurant category */
    private int mNumberRestaurantObjects = 6;
    /* Handles the address from the {@link Place} objects that belong to the restaurant category */
    private String[] mAddress = new String[mNumberRestaurantObjects];
    /* Handles the card image from the {@link Place} objects that belong to the restaurant category */
    private int[] mCardImage = new int[mNumberRestaurantObjects];
    /* Handles the gps coordinates from the {@link Place} objects that belong to the restaurant category */
    private double[][] mCoordinates = new double[mNumberRestaurantObjects][];
    /* Handles the description from the {@link Place} objects that belong to the restaurant category */
    private String[] mDescription = new String[mNumberRestaurantObjects];
    /* Handles the e-mail from the {@link Place} objects that belong to the restaurant category */
    private String[] mEmail = new String[mNumberRestaurantObjects];
    /* Handles the phone number from the {@link Place} objects that belong to the restaurant category */
    private String[] mPhone = new String[mNumberRestaurantObjects];
    /* Handles the opening hours from the {@link Place} objects that belong to the restaurant category */
    private String[][] mOpeningHours = new String[mNumberRestaurantObjects][];
    /* Handles the subtitle from the {@link Place} objects that belong to the restaurant category */
    private String[] mSubtitle = new String[mNumberRestaurantObjects];
    /* Handles the title from the {@link Place} objects that belong to the restaurant category */
    private String[] mTitle = new String[mNumberRestaurantObjects];
    /* Handles the website url from the {@link Place} objects that belong to the restaurant category */
    private String[] mWebsiteURL = new String[mNumberRestaurantObjects];

    /**
     * Required empty public constructor
     */
    public RestaurantFragment() {
        /* Empty */
    }

    /**
     * Override the onCreateView method to create a custom LisView to display the park and sport {@link Place} objects
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return cultureView
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /* Inflates the restaurant's Place objects ListView */
        View parkSportView = inflater.inflate(R.layout.layout_place_list, container, false);

        /* Initialize mAddress[] with the values for the existing {@link Place} object
         */
        mAddress[0] = getString(R.string.restaurant_000_address);
        mAddress[1] = getString(R.string.restaurant_001_address);
        mAddress[2] = getString(R.string.restaurant_002_address);
        mAddress[3] = getString(R.string.restaurant_003_address);
        mAddress[4] = getString(R.string.restaurant_004_address);
        mAddress[5] = getString(R.string.restaurant_005_address);

        /* Initialize mCardImage[] with the values for the existing {@link Place} object
         */
        mCardImage[0] = R.drawable.card_david_park;
        mCardImage[1] = R.drawable.card_forno;
        mCardImage[2] = R.drawable.card_pinheiro;
        mCardImage[3] = R.drawable.card_toucinho;
        mCardImage[4] = R.drawable.card_tertulia_quinta;
        mCardImage[5] = R.drawable.card_zezano;

        /* Initialize mCoordinates[] with the values for the existing {@link Place} object
         */
        mCoordinates[0] = new double[]{39.202309, -8.627494};
        mCoordinates[1] = new double[]{39.202217, -8.627397};
        mCoordinates[2] = new double[]{39.201510, -8.626766};
        mCoordinates[3] = new double[]{39.203152, -8.627266};
        mCoordinates[4] = new double[]{39.201663, -8.626962};
        mCoordinates[5] = new double[]{39.202581, -8.627643};

        /* Initialize mDescription[] with the values for the existing {@link Place} object
         */
        mDescription[0] = getString(R.string.restaurant_000_description);
        mDescription[1] = getString(R.string.restaurant_001_description);
        mDescription[2] = getString(R.string.restaurant_002_description);
        mDescription[3] = getString(R.string.restaurant_003_description);
        mDescription[4] = getString(R.string.restaurant_004_description);
        mDescription[5] = getString(R.string.restaurant_005_description);

        /* Initialize mEmail[] with the values for the existing {@link Place} object
         * it CAN have null values
         */
        mEmail[0] = null;
        mEmail[1] = null;
        mEmail[2] = null;
        mEmail[3] = null;
        mEmail[4] = getString(R.string.restaurant_004_email);
        mEmail[5] = null;

        /* Initialize mOpeningHours[] with the values for the existing {@link Place} object
         * it CAN have null values
         */
        mOpeningHours[0] = null;
        mOpeningHours[1] = new String[]{getString(R.string.restaurant_001_opening_hours_01), getString(R.string.restaurant_001_opening_hours_02)};
        mOpeningHours[2] = new String[]{getString(R.string.restaurant_001_opening_hours_01), getString(R.string.restaurant_001_opening_hours_02)};
        mOpeningHours[3] = new String[]{getString(R.string.restaurant_001_opening_hours_01), getString(R.string.restaurant_001_opening_hours_02)};
        mOpeningHours[4] = new String[]{getString(R.string.restaurant_001_opening_hours_01), getString(R.string.restaurant_001_opening_hours_02)};
        mOpeningHours[5] = null;

        /* Initialize mPhone[] with the values for the existing {@link Place} object
         * it CAN have null values
         */
        mPhone[0] = getString(R.string.restaurant_000_phone);
        mPhone[1] = getString(R.string.restaurant_001_phone);
        mPhone[2] = getString(R.string.restaurant_002_phone);
        mPhone[3] = getString(R.string.restaurant_003_phone);
        mPhone[4] = getString(R.string.restaurant_004_phone);
        mPhone[5] = getString(R.string.restaurant_005_phone);

        /* Initialize mSubtitle[] with the values for the existing {@link Place} object
         * it CAN have null values
         */
        mSubtitle[0] = null;
        mSubtitle[1] = null;
        mSubtitle[2] = null;
        mSubtitle[3] = getString(R.string.restaurant_003_subtitle);
        mSubtitle[4] = null;
        mSubtitle[5] = null;

        /* Initialize mTitle[] with the values for the existing {@link Place} object
         */
        mTitle[0] = getString(R.string.restaurant_000_title);
        mTitle[1] = getString(R.string.restaurant_001_title);
        mTitle[2] = getString(R.string.restaurant_002_title);
        mTitle[3] = getString(R.string.restaurant_003_title);
        mTitle[4] = getString(R.string.restaurant_004_title);
        mTitle[5] = getString(R.string.restaurant_005_title);

        /* Initialize mWebsiteURL[] with the values for the existing {@link Place} object
         */
        mWebsiteURL[0] = getString(R.string.restaurant_000_website_url);
        mWebsiteURL[1] = getString(R.string.restaurant_001_website_url);
        mWebsiteURL[2] = null;
        mWebsiteURL[3] = getString(R.string.restaurant_003_website_url);
        mWebsiteURL[4] = getString(R.string.restaurant_004_website_url);
        mWebsiteURL[5] = null;

        /* Create an ArrayList that stores objects of type Place. Those objects contain the data about
         * the places shown in the app.
         * The Place objects are instantiated with the Place class.
         */
        final ArrayList<Place> places = new ArrayList<Place>();

        for (int index = 0; index < mNumberRestaurantObjects; index++) {
            places.add(new Place(mAddress[index],
                    mCardImage[index],
                    getString(R.string.culture_category),
                    mCoordinates[index],
                    mDescription[index],
                    mEmail[index],
                    mOpeningHours[index],
                    mPhone[index],
                    mSubtitle[index],
                    mTitle[index],
                    mWebsiteURL[index]));
        }

        /* Create a custom {@link PlaceAdapter}, whose data source is a list of {@link Place} objects.
         * The adapter uses list_item_card.xml layout to create layouts for each item in the list.
         */
        PlaceAdapter placeAdapter = new PlaceAdapter(getActivity(), places);

        /* Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
         * There should be a {@link ListView} with the view ID called list, which is declared in the
         * word_list.xml file.
         */
        ListView listView = (ListView) parkSportView.findViewById(R.id.list);

        /* Make the {@link ListView} use the {@link PlaceAdapter} we created above, so that the
         * {@link ListView} will display list items for each place in the list of places.
         * Do this by calling the setAdapter method on the {@link ListView} object and pass in
         */
        listView.setAdapter(placeAdapter);

        /* Set OnItemClickListener in the ListView items */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /* Get the selected {@link Place} object */
                Place selectedPlace = places.get(position);

                /* Create Intent to open the Place Details Activity */
                Intent placeDetailsIntent = new Intent(getContext(), PlaceDetailsActivity.class);

                /* Send song's details to Details Activity */
                placeDetailsIntent.putExtra("ADDRESS", selectedPlace.getAddress());
                placeDetailsIntent.putExtra("CARD_IMAGE", selectedPlace.getCardImageResourceID());
                placeDetailsIntent.putExtra("COORDINATES", selectedPlace.getCoordinates());
                placeDetailsIntent.putExtra("DESCRIPTION", selectedPlace.getDescription());
                placeDetailsIntent.putExtra("EMAIL", selectedPlace.getEmail());
                placeDetailsIntent.putExtra("OPENING_HOURS", selectedPlace.getOpeningHours());
                placeDetailsIntent.putExtra("PHONE", selectedPlace.getPhone());
                placeDetailsIntent.putExtra("SUBTITLE", selectedPlace.getSubtitle());
                placeDetailsIntent.putExtra("TITLE", selectedPlace.getTitle());
                placeDetailsIntent.putExtra("WEBSITE_URL", selectedPlace.getWebsiteURL());

                /* Start the detailsIntent (Place Details Activity) */
                getContext().startActivity(placeDetailsIntent);
            }
        });

        /* Returns the culture's Place objects ListView */
        return parkSportView;
    }
}
