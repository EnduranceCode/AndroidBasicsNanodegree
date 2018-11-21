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
 * {@link Fragment} that displays a list of category hotel places.
 */
public class HotelFragment extends Fragment {
    /**
     * To facilitate the addition of new {@link Place} objects and the edition of existing {@link Place}
     * we will store its parameters in arrays
     */
    /* Handles the number existent {@link Place} objects that belong to the hotel category */
    private int mNumberHotelObjects = 6;
    /* Handles the address from the {@link Place} objects that belong to the hotel category */
    private String[] mAddress = new String[mNumberHotelObjects];
    /* Handles the card image from the {@link Place} objects that belong to the hotel category */
    private int[] mCardImage = new int[mNumberHotelObjects];
    /* Handles the gps coordinates from the {@link Place} objects that belong to the hotel category */
    private double[][] mCoordinates = new double[mNumberHotelObjects][];
    /* Handles the description from the {@link Place} objects that belong to the hotel category */
    private String[] mDescription = new String[mNumberHotelObjects];
    /* Handles the e-mail from the {@link Place} objects that belong to the hotel category */
    private String[] mEmail = new String[mNumberHotelObjects];
    /* Handles the phone number from the {@link Place} objects that belong to the hotel category */
    private String[] mPhone = new String[mNumberHotelObjects];
    /* Handles the opening hours from the {@link Place} objects that belong to the hotel category */
    private String[][] mOpeningHours = new String[mNumberHotelObjects][];
    /* Handles the subtitle from the {@link Place} objects that belong to the hotel category */
    private String[] mSubtitle = new String[mNumberHotelObjects];
    /* Handles the title from the {@link Place} objects that belong to the hotel category */
    private String[] mTitle = new String[mNumberHotelObjects];
    /* Handles the website url from the {@link Place} objects that belong to the hotel category */
    private String[] mWebsiteURL = new String[mNumberHotelObjects];

    /**
     * Required empty public constructor
     */
    public HotelFragment() {
        /* Empty */
    }

    /**
     * Override the onCreateView method to create a custom LisView to display the hotel's {@link Place} objects
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return cultureView
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /* Inflates the hotel's Place objects ListView */
        View hotelView = inflater.inflate(R.layout.layout_place_list, container, false);

        /* Initialize mAddress[] with the values for the existing {@link Place} object
         */
        mAddress[0] = "Quinta do Variorum\nEstrada do Arneiro\nArneiro da Volta\n2080-074 Almeirim";
        mAddress[1] = "Travessa do Calvário, 2\n2080-174 Almeirim\"";
        mAddress[2] = "Rua Alfredo Godinho, 6\n2080-067 Almeirim";
        mAddress[3] = "Rua de Timor, 4\n2080-103 Almeirim";
        mAddress[4] = "Rua de Timor, 1\n2080-103 Almeirim";
        mAddress[5] = "Almeirim\n2080-052 Almeirim";

        /* Initialize mCardImage[] with the values for the existing {@link Place} object
         */
        mCardImage[0] = R.drawable.card_besteiros;
        mCardImage[1] = R.drawable.card_hospedaria_abilio;
        mCardImage[2] = R.drawable.card_dom_antonio;
        mCardImage[3] = R.drawable.card_minhoto;
        mCardImage[4] = R.drawable.card_novo_principe;
        mCardImage[5] = R.drawable.card_gafaria;

        /* Initialize mCoordinates[] with the values for the existing {@link Place} object
         */
        mCoordinates[0] = new double[]{39.140190, -8.505919};
        mCoordinates[1] = new double[]{39.207665, -8.631683};
        mCoordinates[2] = new double[]{39.205319, -8.629049};
        mCoordinates[3] = new double[]{39.203139, -8.627195};
        mCoordinates[4] = new double[]{39.204145, -8.628732};
        mCoordinates[5] = new double[]{39.222943, -8.655270};

        /* Initialize mDescription[] with the values for the existing {@link Place} object
         */
        mDescription[0] = getString(R.string.hotel_000_description);
        mDescription[1] = getString(R.string.hotel_001_description);
        mDescription[2] = getString(R.string.hotel_002_description);
        mDescription[3] = getString(R.string.hotel_003_description);
        mDescription[4] = getString(R.string.hotel_004_description);
        mDescription[5] = getString(R.string.hotel_005_description);

        /* Initialize mEmail[] with the values for the existing {@link Place} object
         * it CAN have null values
         */
        mEmail[0] = "agroturismo@casadebesteiros.pt";
        mEmail[1] = "reservas@hospedariaoabilio.com";
        mEmail[2] = null;
        mEmail[3] = null;
        mEmail[4] = "geral@hotelonovoprincipe.com";
        mEmail[5] = "reservas.quintagafaria@gmail.com";

        /* Initialize mOpeningHours[] with the values for the existing {@link Place} object
         * it CAN have null values
         */
        mOpeningHours[0] = null;
        mOpeningHours[1] = new String[]{getString(R.string.hotel_001_opening_hours_01)};
        mOpeningHours[2] = null;
        mOpeningHours[3] = null;
        mOpeningHours[4] = new String[]{getString(R.string.hotel_004_opening_hours_01)};
        mOpeningHours[5] = null;

        /* Initialize mPhone[] with the values for the existing {@link Place} object
         * it CAN have null values
         */
        mPhone[0] = "243 595 474";
        mPhone[1] = "243 592 602";
        mPhone[2] = "243 593 263";
        mPhone[3] = "243 592 057";
        mPhone[4] = "243 570 600";
        mPhone[5] = "919 762 584";

        /* Initialize mSubtitle[] with the values for the existing {@link Place} object
         * it CAN have null values
         */
        mSubtitle[0] = null;
        mSubtitle[1] = getString(R.string.hotel_001_subtitle);
        mSubtitle[2] = null;
        mSubtitle[3] = null;
        mSubtitle[4] = null;
        mSubtitle[5] = getString(R.string.hotel_005_subtitle);

        /* Initialize mTitle[] with the values for the existing {@link Place} object
         */
        mTitle[0] = getString(R.string.hotel_000_title);
        mTitle[1] = getString(R.string.hotel_001_title);
        mTitle[2] = getString(R.string.hotel_002_title);
        mTitle[3] = getString(R.string.hotel_003_title);
        mTitle[4] = getString(R.string.hotel_004_title);
        mTitle[5] = getString(R.string.hotel_005_title);

        /* Initialize mWebsiteURL[] with the values for the existing {@link Place} object
         */
        mWebsiteURL[0] = "www.casadebesteiros.pt";
        mWebsiteURL[1] = "www.hospedariaoabilio.com";
        mWebsiteURL[2] = null;
        mWebsiteURL[3] = null;
        mWebsiteURL[4] = "http://hotelonovoprincipe.com";
        mWebsiteURL[5] = "http://www.quintadagafaria.com";

        /* Create an ArrayList that stores objects of type Place. Those objects contain the data about
         * the places shown in the app.
         * The Place objects are instantiated with the Place class.
         */
        final ArrayList<Place> places = new ArrayList<Place>();

        for (int index = 0; index < mNumberHotelObjects; index++) {
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
        ListView listView = (ListView) hotelView.findViewById(R.id.list);

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
        return hotelView;
    }
}
