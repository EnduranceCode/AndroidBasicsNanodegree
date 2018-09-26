package com.endurancecode.miwok;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NumbersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        String[] englishNumbers = new String[10];
        englishNumbers[0] = "one";
        englishNumbers[1] = "two";
        englishNumbers[2] = "three";
        englishNumbers[3] = "four";
        englishNumbers[4] = "five";
        englishNumbers[5] = "six";
        englishNumbers[6] = "seven";
        englishNumbers[7] = "eight";
        englishNumbers[8] = "nine";
        englishNumbers[9] = "ten";
    }
}
