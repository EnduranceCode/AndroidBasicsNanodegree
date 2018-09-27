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

        // Add the TextViews with the english words for numbers to the Numbers layout
        TextView wordView0 = new TextView(this);
        wordView0.setText(englishNumbers.get(0));
        rootView.addView(wordView0);

        TextView wordView1 = new TextView(this);
        wordView1.setText(englishNumbers.get(1));
        rootView.addView(wordView1);

        TextView wordView2 = new TextView(this);
        wordView2.setText(englishNumbers.get(2));
        rootView.addView(wordView2);

        TextView wordView3 = new TextView(this);
        wordView3.setText(englishNumbers.get(3));
        rootView.addView(wordView3);

        TextView wordView4 = new TextView(this);
        wordView4.setText(englishNumbers.get(4));
        rootView.addView(wordView4);

        TextView wordView5 = new TextView(this);
        wordView5.setText(englishNumbers.get(5));
        rootView.addView(wordView5);

        TextView wordView6 = new TextView(this);
        wordView6.setText(englishNumbers.get(6));
        rootView.addView(wordView6);

        TextView wordView7 = new TextView(this);
        wordView7.setText(englishNumbers.get(7));
        rootView.addView(wordView7);

        TextView wordView8 = new TextView(this);
        wordView8.setText(englishNumbers.get(8));
        rootView.addView(wordView8);

        TextView wordView9 = new TextView(this);
        wordView9.setText(englishNumbers.get(9));
        rootView.addView(wordView9);
    }
}
