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
public class ParkSportFragment extends Fragment {
    /**
     * To facilitate the addition of new {@link Place} objects and the edition of existing {@link Place}
     * we will store its parameters in arrays
     */
    /* Handles the number existent {@link Place} objects that belong to the park and sport category */
    private int mNumberParkSportObjects = 6;
    /* Handles the address from the {@link Place} objects that belong to the park and sport category */
    private String[] mAddress = new String[mNumberParkSportObjects];
    /* Handles the card image from the {@link Place} objects that belong to the park and sport category */
    private int[] mCardImage = new int[mNumberParkSportObjects];
    /* Handles the gps coordinates from the {@link Place} objects that belong to the park and sport category */
    private double[][] mCoordinates = new double[mNumberParkSportObjects][];
    /* Handles the description from the {@link Place} objects that belong to the park and sport category */
    private String[] mDescription = new String[mNumberParkSportObjects];
    /* Handles the e-mail from the {@link Place} objects that belong to the park and sport category */
    private String[] mEmail = new String[mNumberParkSportObjects];
    /* Handles the phone number from the {@link Place} objects that belong to the park and sport category */
    private String[] mPhone = new String[mNumberParkSportObjects];
    /* Handles the opening hours from the {@link Place} objects that belong to the park and sport category */
    private String[][] mOpeningHours = new String[mNumberParkSportObjects][];
    /* Handles the subtitle from the {@link Place} objects that belong to the park and sport category */
    private String[] mSubtitle = new String[mNumberParkSportObjects];
    /* Handles the title from the {@link Place} objects that belong to the park and sport category */
    private String[] mTitle = new String[mNumberParkSportObjects];
    /* Handles the website url from the {@link Place} objects that belong to the park and sport category */
    private String[] mWebsiteURL = new String[mNumberParkSportObjects];

    /**
     * Required empty public constructor
     */
    public ParkSportFragment() {
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
        /* Inflates the park and sport's Place objects ListView */
        View parkSportView = inflater.inflate(R.layout.layout_place_list, container, false);

        /* Initialize mAddress[] with the values for the existing {@link Place} object
         */
        mAddress[0] = "Rua do Campo da Bola\n2080-147 Almeirim";
        mAddress[1] = "Av. D Joáo I\n2080-014 Almeirim";
        mAddress[2] = "Praça da República\n2080-000 Almeirim";
        mAddress[3] = "Av. D Joáo I\n2080-014 Almeirim";
        mAddress[4] = "Rua Condessa da Junqueira\n2080-069 Almeirim ";
        mAddress[5] = "Av. D Joáo I\n2080-014 Almeirim";

        /* Initialize mCardImage[] with the values for the existing {@link Place} object
         */
        mCardImage[0] = R.drawable.card_circuito_manutencao;
        mCardImage[1] = R.drawable.card_estadio_municipal;
        mCardImage[2] = R.drawable.card_jardim_republica;
        mCardImage[3] = R.drawable.card_parque_norte;
        mCardImage[4] = R.drawable.card_parque_desportivo;
        mCardImage[5] = R.drawable.card_piscinas;

        /* Initialize mCoordinates[] with the values for the existing {@link Place} object
         */
        mCoordinates[0] = new double[]{39.211005, -8.611846};
        mCoordinates[1] = new double[]{39.212705, -8.616699};
        mCoordinates[2] = new double[]{39.209675, -8.630000};
        mCoordinates[3] = new double[]{39.214042, -8.620314};
        mCoordinates[4] = new double[]{39.205870, -8.625168};
        mCoordinates[5] = new double[]{39.213499, -8.617450};

        /* Initialize mDescription[] with the values for the existing {@link Place} object
         */
        mDescription[0] = getString(R.string.park_sport_000_description);
        mDescription[1] = getString(R.string.park_sport_001_description);
        mDescription[2] = getString(R.string.park_sport_002_description);
        mDescription[3] = getString(R.string.park_sport_003_description);
        mDescription[4] = getString(R.string.park_sport_004_description);
        mDescription[5] = getString(R.string.park_sport_005_description);

        /* Initialize mEmail[] with the values for the existing {@link Place} object
         * it CAN have null values
         */
        mEmail[0] = null;
        mEmail[1] = null;
        mEmail[2] = null;
        mEmail[3] = null;
        mEmail[4] = null;
        mEmail[5] = null;

        /* Initialize mOpeningHours[] with the values for the existing {@link Place} object
         * it CAN have null values
         */
        mOpeningHours[0] = null;
        mOpeningHours[1] = null;
        mOpeningHours[2] = null;
        mOpeningHours[3] = null;
        mOpeningHours[4] = null;
        mOpeningHours[5] = new String[]{getString(R.string.park_sport_005_opening_hours_01), getString(R.string.park_sport_005_opening_hours_02)};

        /* Initialize mPhone[] with the values for the existing {@link Place} object
         * it CAN have null values
         */
        mPhone[0] = null;
        mPhone[1] = null;
        mPhone[2] = null;
        mPhone[3] = null;
        mPhone[4] = null;
        mPhone[5] = "243 594 130";

        /* Initialize mSubtitle[] with the values for the existing {@link Place} object
         * it CAN have null values
         */
        mSubtitle[0] = null;
        mSubtitle[1] = null;
        mSubtitle[2] = null;
        mSubtitle[3] = null;
        mSubtitle[4] = null;
        mSubtitle[5] = null;

        /* Initialize mTitle[] with the values for the existing {@link Place} object
         */
        mTitle[0] = getString(R.string.park_sport_000_title);
        mTitle[1] = getString(R.string.park_sport_001_title);
        mTitle[2] = getString(R.string.park_sport_002_title);
        mTitle[3] = getString(R.string.park_sport_003_title);
        mTitle[4] = getString(R.string.park_sport_004_title);
        mTitle[5] = getString(R.string.park_sport_005_title);

        /* Initialize mWebsiteURL[] with the values for the existing {@link Place} object
         */
        mWebsiteURL[0] = null;
        mWebsiteURL[1] = null;
        mWebsiteURL[2] = null;
        mWebsiteURL[3] = null;
        mWebsiteURL[4] = null;
        mWebsiteURL[5] = null;

        /* Create an ArrayList that stores objects of type Place. Those objects contain the data about
         * the places shown in the app.
         * The Place objects are instantiated with the Place class.
         */
        final ArrayList<Place> places = new ArrayList<Place>();

        for (int index = 0; index < mNumberParkSportObjects; index++) {
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
