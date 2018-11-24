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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        /* Create an ArrayList that stores objects of type {@link Earthquake}
         */
        ArrayList<Earthquake> earthquakes = new ArrayList<>();
        earthquakes.add(new Earthquake(6.2, "San Francisco", 0));
        earthquakes.add(new Earthquake(6.2, "London", 0));
        earthquakes.add(new Earthquake(6.2, "Tokyo", 0));
        earthquakes.add(new Earthquake(6.2, "Mexico City", 0));
        earthquakes.add(new Earthquake(6.2, "Moscow", 0));
        earthquakes.add(new Earthquake(6.2, "Rio de Janeiro", 0));
        earthquakes.add(new Earthquake(6.2, "Paris", 0));

        /* Find a reference to the {@link ListView} in the layout */
        ListView earthquakeListView = findViewById(R.id.list);

        /* Create a new {@link ArrayAdapter} of earthquakes */
        EarthquakeAdapter earthquakeAdapter = new EarthquakeAdapter(this, earthquakes);

        /* Set the adapter on the {@link ListView} so the list can be populated in the user interface */
        earthquakeListView.setAdapter(earthquakeAdapter);
    }
}
