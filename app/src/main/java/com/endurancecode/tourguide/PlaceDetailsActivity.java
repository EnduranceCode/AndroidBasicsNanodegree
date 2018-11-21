package com.endurancecode.tourguide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PlaceDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Set the content of the activity to use the song_details.xml layout file */
        setContentView(R.layout.layout_place_details);

        /* Provide up navigation following documentation at
         * https://developer.android.com/training/implementing-navigation/ancestral
         */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /* Stores the data sent from SongAdapter */
        String address = getIntent().getStringExtra("ADDRESS");
        int cardImage = getIntent().getIntExtra("CARD_IMAGE", 0);
        double[] coordinates = getIntent().getDoubleArrayExtra("COORDINATES");
        String description = getIntent().getStringExtra("DESCRIPTION");
        String email = getIntent().getStringExtra("EMAIL");
        String[] openingHours = getIntent().getStringArrayExtra("OPENING_HOURS");
        String phone = getIntent().getStringExtra("PHONE");
        String subtitle = getIntent().getStringExtra("SUBTITLE");
        String title = getIntent().getStringExtra("TITLE");
        String websiteURL = getIntent().getStringExtra("WEBSITE_URL");

        /* Find the views to set with the received data */
        TextView addressTextView = (TextView) findViewById(R.id.address_text);
        ImageView cardImageView = (ImageView) findViewById(R.id.card_image);
        View coordinatesView = (View) findViewById(R.id.coordinates);
        TextView coordinatesTextView = (TextView) findViewById(R.id.coordinates_text);
        TextView descriptionTextView = (TextView) findViewById(R.id.description_text);
        View emailView = (View) findViewById(R.id.email);
        TextView emailTextView = (TextView) findViewById(R.id.email_text);
        View openingHoursView = (View) findViewById(R.id.opening_hours);
        TextView openingHoursTextView = (TextView) findViewById(R.id.opening_hours_text);
        View phoneView = (View) findViewById(R.id.phone);
        TextView phoneTextView = (TextView) findViewById(R.id.phone_text);
        TextView subtitleTextView = (TextView) findViewById(R.id.subtitle_text);
        TextView titleTextView = (TextView) findViewById(R.id.title_text);
        View websiteUrlView = (View) findViewById(R.id.website_url);
        TextView websiteUrlTextView = (TextView) findViewById(R.id.website_url_text);

        /* Set the received data in the details place layout
         */
        /* Set the address */
        addressTextView.setText(address);

        /* Set the cover image */
        cardImageView.setImageResource(cardImage);

        /* Set the gps coordinates */
        String coordinatesTextDisplay = String.valueOf(coordinates[0]) + ", " + String.valueOf(coordinates[1]);
        coordinatesTextView.setText(coordinatesTextDisplay);

        /* Set the description */
        descriptionTextView.setText(description);

        /* Check if an e-mail exists and if so set it */
        if (email != null) {
            emailView.setVisibility(View.VISIBLE);
            emailTextView.setText(email);
        } else {
            emailView.setVisibility(View.GONE);
        }

        /* Check if opening hours exists and if so set it */
        if (openingHours != null) {
            openingHoursView.setVisibility(View.VISIBLE);
            String openingHoursTextDisplay = "";
            for (int index = 0; index < openingHours.length; index++) {
                openingHoursTextDisplay = openingHoursTextDisplay + openingHours[index];
                if (index != openingHours.length - 1) {
                    openingHoursTextDisplay += "\n";
                }
            }
            openingHoursTextView.setText(openingHoursTextDisplay);
        } else {
            openingHoursView.setVisibility(View.GONE);
        }

        /* Check if a phone number exists and if so set it */
        if (phone != null) {
            phoneView.setVisibility(View.VISIBLE);
            phoneTextView.setText(phone);
        } else {
            phoneView.setVisibility(View.GONE);
        }

        /* Check if a subtitle exists and if so set it */
        if (subtitle != null) {
            subtitleTextView.setVisibility(View.VISIBLE);
            subtitleTextView.setText(subtitle);
        } else {
            subtitleTextView.setVisibility(View.GONE);
        }

        /* Check if an website url exists and if so set it */
        titleTextView.setText(title);

        if (websiteURL != null) {
            websiteUrlView.setVisibility(View.VISIBLE);
            websiteUrlTextView.setText(websiteURL);
        } else {
            websiteUrlView.setVisibility(View.GONE);
        }
    }
}
