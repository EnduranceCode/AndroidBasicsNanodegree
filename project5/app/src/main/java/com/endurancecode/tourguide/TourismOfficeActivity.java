package com.endurancecode.tourguide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class TourismOfficeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tourism_office_activity);

        /* Provide up navigation following documentation at
         * https://developer.android.com/training/implementing-navigation/ancestral
         */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
