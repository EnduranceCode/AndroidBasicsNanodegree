package com.endurancecode.tourguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Find the culture's category card */
        View cultureCardView = (View) findViewById(R.id.culture);

        cultureCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View cardView) {
                Intent cultureFragmentIntent = new Intent(MainActivity.this, CardsListActivity.class);
                cultureFragmentIntent.putExtra("CHOSEN_ITEM", 0);
                startActivity(cultureFragmentIntent);
            }
        });

        /* Find the park and sport's category card */
        View parkSportCardView = (View) findViewById(R.id.park_sport);

        parkSportCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View cardView) {
                Intent cultureFragmentIntent = new Intent(MainActivity.this, CardsListActivity.class);
                cultureFragmentIntent.putExtra("CHOSEN_ITEM", 1);
                startActivity(cultureFragmentIntent);
            }
        });

        /* Find the park and sport's category card */
        View restaurantCardView = (View) findViewById(R.id.restaurants);

        restaurantCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View cardView) {
                Intent cultureFragmentIntent = new Intent(MainActivity.this, CardsListActivity.class);
                cultureFragmentIntent.putExtra("CHOSEN_ITEM", 2);
                startActivity(cultureFragmentIntent);
            }
        });

        /* Find the park and sport's category card */
        View hotelCardView = (View) findViewById(R.id.hotels);

        hotelCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View cardView) {
                Intent cultureFragmentIntent = new Intent(MainActivity.this, CardsListActivity.class);
                cultureFragmentIntent.putExtra("CHOSEN_ITEM", 3);
                startActivity(cultureFragmentIntent);
            }
        });
    }
}
