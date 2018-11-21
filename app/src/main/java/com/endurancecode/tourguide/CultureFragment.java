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
 * {@link Fragment} that displays a list of category culture places.
 */
public class CultureFragment extends Fragment {
    /**
     * To facilitate the addition of new {@link Place} objects and the edition of existing {@link Place}
     * we will store its parameters in arrays
     */
    /* Handles the number existent {@link Place} objects that belong to the culture category */
    private int mNumberCultureObjects = 7;
    /* Handles the address from the {@link Place} objects that belong to the culture category */
    private String[] mAddress = new String[mNumberCultureObjects];
    /* Handles the card image from the {@link Place} objects that belong to the culture category */
    private int[] mCardImage = new int[mNumberCultureObjects];
    /* Handles the gps coordinates from the {@link Place} objects that belong to the culture category */
    private double[][] mCoordinates = new double[mNumberCultureObjects][];
    /* Handles the description from the {@link Place} objects that belong to the culture category */
    private String[] mDescription = new String[mNumberCultureObjects];
    /* Handles the e-mail from the {@link Place} objects that belong to the culture category */
    private String[] mEmail = new String[mNumberCultureObjects];
    /* Handles the phone number from the {@link Place} objects that belong to the culture category */
    private String[] mPhone = new String[mNumberCultureObjects];
    /* Handles the opening hours from the {@link Place} objects that belong to the culture category */
    private String[][] mOpeningHours = new String[mNumberCultureObjects][];
    /* Handles the subtitle from the {@link Place} objects that belong to the culture category */
    private String[] mSubtitle = new String[mNumberCultureObjects];
    /* Handles the title from the {@link Place} objects that belong to the culture category */
    private String[] mTitle = new String[mNumberCultureObjects];
    /* Handles the website url from the {@link Place} objects that belong to the culture category */
    private String[] mWebsiteURL = new String[mNumberCultureObjects];

    /**
     * Required empty public constructor
     */
    public CultureFragment() {
        /* Empty */
    }

    /**
     * Override the onCreateView method to create a custom LisView to display the culture's {@link Place} objects
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return cultureView
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /* Inflates the culture's Place objects ListView */
        View cultureView = inflater.inflate(R.layout.layout_place_list, container, false);

        /* Initialize mAddress[] with the values for the existing {@link Place} object
         */
        mAddress[0] = "Parque Alfredo Bento Calado\nAvenida 25 de Abril\n2080-094 Almeirim";
        mAddress[1] = "Rua Marechal Carmona\n2080-587 Fazendas de Almeirim";
        mAddress[2] = "Praça da Républica\n2080-044 Almeirim";
        mAddress[3] = "Rua Dionísio Saraiva\n2080-052 Almeirim";
        mAddress[4] = "Rua Conde da Taipa\n2080-126 Almeirim";
        mAddress[5] = "Centro Coordenador de Transportes Terrestres\nRua de Coruche\n2080-094 Almeirim";
        mAddress[6] = "Largo da Praça de Touros\n2080-030 Almeirim";

        /* Initialize mCardImage[] with the values for the existing {@link Place} object
         */
        mCardImage[0] = R.drawable.card_biblioteca_municipal;
        mCardImage[1] = R.drawable.card_centro_cultural;
        mCardImage[2] = R.drawable.card_cine_teatro;
        mCardImage[3] = R.drawable.card_galeria_municipal;
        mCardImage[4] = R.drawable.card_igreja_matriz;
        mCardImage[5] = R.drawable.card_museu_municipal;
        mCardImage[6] = R.drawable.card_praca_toiros;

        /* Initialize mCoordinates[] with the values for the existing {@link Place} object
         */
        mCoordinates[0] = new double[]{39.207355, -8.621522};
        mCoordinates[1] = new double[]{39.174352, -8.584860};
        mCoordinates[2] = new double[]{39.174489, -8.584472};
        mCoordinates[3] = new double[]{39.208902, -8.628723};
        mCoordinates[4] = new double[]{39.212828, -8.627397};
        mCoordinates[5] = new double[]{39.199119, -8.627675};
        mCoordinates[6] = new double[]{39.201168, -8.627280};

        /* Initialize mDescription[] with the values for the existing {@link Place} object
         */
        mDescription[0] = getString(R.string.culture_000_description);
        mDescription[1] = getString(R.string.culture_001_description);
        mDescription[2] = getString(R.string.culture_002_description);
        mDescription[3] = getString(R.string.culture_003_description);
        mDescription[4] = getString(R.string.culture_004_description);
        mDescription[5] = getString(R.string.culture_005_description);
        mDescription[6] = getString(R.string.culture_006_description);

        /* Initialize mEmail[] with the values for the existing {@link Place} object
         * it CAN have null values
         */
        mEmail[0] = "biblioteca@almeirim.pt";
        mEmail[1] = "actividadesculturais@cm-almeirim.pt";
        mEmail[2] = "actividadesculturais@cm-almeirim.pt";
        mEmail[3] = null;
        mEmail[4] = null;
        mEmail[5] = "museu@cm-almeirim.pt";
        mEmail[6] = null;

        /* Initialize mOpeningHours[] with the values for the existing {@link Place} object
         * it CAN have null values
         */
        mOpeningHours[0] = new String[]{getString(R.string.culture_000_opening_hours_01), getString(R.string.culture_000_opening_hours_02)};
        mOpeningHours[1] = new String[]{getString(R.string.culture_001_opening_hours_01)};
        mOpeningHours[2] = new String[]{getString(R.string.culture_002_opening_hours_01)};
        mOpeningHours[3] = new String[]{getString(R.string.culture_003_opening_hours_01)};
        mOpeningHours[4] = null;
        mOpeningHours[5] = new String[]{getString(R.string.culture_005_opening_hours_01)};
        mOpeningHours[6] = null;

        /* Initialize mPhone[] with the values for the existing {@link Place} object
         * it CAN have null values
         */
        mPhone[0] = "243 594 122";
        mPhone[1] = null;
        mPhone[2] = null;
        mPhone[3] = null;
        mPhone[4] = null;
        mPhone[5] = "243 594 136";
        mPhone[6] = "243 594 020";

        /* Initialize mSubtitle[] with the values for the existing {@link Place} object
         * it CAN have null values
         */
        mSubtitle[0] = null;
        mSubtitle[1] = null;
        mSubtitle[2] = null;
        mSubtitle[3] = null;
        mSubtitle[4] = null;
        mSubtitle[5] = null;
        mSubtitle[6] = null;

        /* Initialize mTitle[] with the values for the existing {@link Place} object
         */
        mTitle[0] = getString(R.string.culture_000_title);
        mTitle[1] = getString(R.string.culture_001_title);
        mTitle[2] = getString(R.string.culture_002_title);
        mTitle[3] = getString(R.string.culture_003_title);
        mTitle[4] = getString(R.string.culture_004_title);
        mTitle[5] = getString(R.string.culture_005_title);
        mTitle[6] = getString(R.string.culture_006_title);

        /* Initialize mWebsiteURL[] with the values for the existing {@link Place} object
         */
        mWebsiteURL[0] = null;
        mWebsiteURL[1] = null;
        mWebsiteURL[2] = null;
        mWebsiteURL[3] = null;
        mWebsiteURL[4] = null;
        mWebsiteURL[5] = "http://museualmeirim.blogspot.com";
        mWebsiteURL[6] = null;

        /* Create an ArrayList that stores objects of type Place. Those objects contain the data about
         * the places shown in the app.
         * The Place objects are instantiated with the Place class.
         */
        final ArrayList<Place> places = new ArrayList<Place>();

        for (int index = 0; index < mNumberCultureObjects; index++) {
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
        ListView listView = (ListView) cultureView.findViewById(R.id.list);

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
        return cultureView;
    }
}
