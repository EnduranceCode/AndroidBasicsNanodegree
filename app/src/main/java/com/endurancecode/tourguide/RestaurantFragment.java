package com.endurancecode.tourguide;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        mAddress[0] = "Largo da Praça de Toiros, 15\n2080-030 Almeirim";
        mAddress[1] = "Largo da Praça de Toiros, 23\n2080-030 Almeirim";
        mAddress[2] = "Largo da Praça de Toiros, 41\n2080-030 Almeirim";
        mAddress[3] = "Rua de Timor, 2 - 5\n2080-095 Almeirim";
        mAddress[4] = "Largo da Praça de Toiros, 37\n2080-030 Almeirim";
        mAddress[5] = "Largo da Praça de Toiros, 5\n2080-030 Almeirim";

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
        mEmail[4] = "reservas@tertuliadaquinta.com";
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
        mPhone[0] = "243 591 475";
        mPhone[1] = "243 241 163";
        mPhone[2] = "243 592 052";
        mPhone[3] = "243 592 237";
        mPhone[4] = "243 593 008";
        mPhone[5] = "243 594 130";

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
        mWebsiteURL[0] = "http://www.davidparque.rbx.pt";
        mWebsiteURL[1] = "http://restauranteoforno.pt";
        mWebsiteURL[2] = null;
        mWebsiteURL[3] = "http://www.toucinho.com";
        mWebsiteURL[4] = "http://www.tertuliadaquinta.com";
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

        /* Create a custom {@link PlaceAdapter}, whose data source is a list of {@link Place} objects. The
         * adapter uses list_item_card.xml layout to create layouts for each item in the list.
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

        /* Returns the culture's Place objects ListView */
        return parkSportView;
    }
}
