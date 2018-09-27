package com.endurancecode.miwok;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        //Create ArrayList to store english words for numbers
        ArrayList<String> englishNumbers = new ArrayList<String>();

        englishNumbers.add("one");
        englishNumbers.add("two");
        englishNumbers.add("three");
        englishNumbers.add("four");
        englishNumbers.add("five");
        englishNumbers.add("six");
        englishNumbers.add("seven");
        englishNumbers.add("eight");
        englishNumbers.add("nine");
        englishNumbers.add("ten");

        // Find the root view in NumbersActivity
        LinearLayout rootView = (LinearLayout) findViewById(R.id.rootView);

        // Set the loop to add the english words for numbers in NumbersActivity root layout
        for (int loopIndex = 0; loopIndex < englishNumbers.size(); loopIndex++) {
            TextView wordView = new TextView(this);
            wordView.setText(englishNumbers.get(loopIndex));
            rootView.addView(wordView);
        }
    }
}
