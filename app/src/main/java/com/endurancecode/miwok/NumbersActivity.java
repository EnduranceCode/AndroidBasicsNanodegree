package com.endurancecode.miwok;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

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

        Log.v("NumbersActivity", "englishNumbers[0]: " + englishNumbers.get(0));
        Log.v("NumbersActivity", "englishNumbers[1]: " + englishNumbers.get(1));
        Log.v("NumbersActivity", "englishNumbers[2]: " + englishNumbers.get(2));
        Log.v("NumbersActivity", "englishNumbers[3]: " + englishNumbers.get(3));
        Log.v("NumbersActivity", "englishNumbers[4]: " + englishNumbers.get(4));
        Log.v("NumbersActivity", "englishNumbers[5]: " + englishNumbers.get(5));
        Log.v("NumbersActivity", "englishNumbers[6]: " + englishNumbers.get(6));
        Log.v("NumbersActivity", "englishNumbers[7]: " + englishNumbers.get(7));
        Log.v("NumbersActivity", "englishNumbers[8]: " + englishNumbers.get(8));
        Log.v("NumbersActivity", "englishNumbers[9]: " + englishNumbers.get(9));
    }
}
