package com.endurancecode.miwok;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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

        Log.v("NumbersActivity", "englishNumbers[0]: " + englishNumbers[0]);
        Log.v("NumbersActivity", "englishNumbers[1]: " + englishNumbers[1]);
        Log.v("NumbersActivity", "englishNumbers[2]: " + englishNumbers[2]);
        Log.v("NumbersActivity", "englishNumbers[3]: " + englishNumbers[3]);
        Log.v("NumbersActivity", "englishNumbers[4]: " + englishNumbers[4]);
        Log.v("NumbersActivity", "englishNumbers[5]: " + englishNumbers[5]);
        Log.v("NumbersActivity", "englishNumbers[6]: " + englishNumbers[6]);
        Log.v("NumbersActivity", "englishNumbers[7]: " + englishNumbers[7]);
        Log.v("NumbersActivity", "englishNumbers[8]: " + englishNumbers[8]);
        Log.v("NumbersActivity", "englishNumbers[9]: " + englishNumbers[9]);
    }
}
