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
package com.endurancecode.miwok;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);

        // Get the Colors Text View
        TextView colorsView = (TextView) findViewById(R.id.colors);

        // Set OnClickListener method for Colors View
        colorsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create Intent to open the Colors Activity
                Intent colorsIntent = new Intent(MainActivity.this, ColorsActivity.class);

                // Start the colorsIntent (Colors Activity)
                startActivity(colorsIntent);
            }
        });

        // Get the Family Text View
        TextView familyView = (TextView) findViewById(R.id.family);

        // Set OnClickListener method for Family View
        familyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create Intent to open the Family Activity
                Intent familyIntent = new Intent(MainActivity.this, FamilyActivity.class);

                // Start the numbersIntent (Numbers Activity)
                startActivity(familyIntent);
            }
        });

        // Get the Number Text View
        TextView numbersView = (TextView) findViewById(R.id.numbers);

        // Set OnClickListener method for Numbers View
        numbersView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create Intent to open the Numbers Activity
                Intent numbersIntent = new Intent(MainActivity.this, NumbersActivity.class);

                // Start the numbersIntent (Numbers Activity)
                startActivity(numbersIntent);
            }
        });

        // Get the Phrases Text View
        TextView phrasesView = (TextView) findViewById(R.id.phrases);

        // Set OnClickListener method for Phrases View
        phrasesView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create Intent to open the Phrases Activity
                Intent phrasesIntent = new Intent(MainActivity.this, PhrasesActivity.class);

                // Start the phrasesIntent (Phrases Activity)
                startActivity(phrasesIntent);
            }
        });
    }
}
