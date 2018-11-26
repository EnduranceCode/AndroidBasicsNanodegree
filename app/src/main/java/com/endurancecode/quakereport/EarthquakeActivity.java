/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.endurancecode.quakereport;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        /*
         * Create an ArrayList that stores objects of type {@link Earthquake} using the method
         * extractEarthquakes() from the {@link QueryUtils} class
         */
        final ArrayList<Earthquake> earthquakes = QueryUtils.extractEarthquakes();

        /* Find a reference to the {@link ListView} in the layout */
        ListView earthquakeListView = findViewById(R.id.list);

        /* Create a new {@link ArrayAdapter} of earthquakes */
        EarthquakeAdapter earthquakeAdapter = new EarthquakeAdapter(this, earthquakes);

        /* Set the adapter on the {@link ListView} so the list can be populated in the user interface */
        earthquakeListView.setAdapter(earthquakeAdapter);

        /* Set OnItemClickListener in the ListView items */
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /* Get the selected {@link Earthquake} object */
                Earthquake selectedEarthquake = earthquakes.get(position);

                /* Set and start a web browser intent with the earthquake url */
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(selectedEarthquake.getUrl()));
                startActivity(browserIntent);
            }
        });
    }
}
