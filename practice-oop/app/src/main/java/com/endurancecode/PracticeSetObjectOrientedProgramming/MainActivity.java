package com.endurancecode.PracticeSetObjectOrientedProgramming;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView profile_picture = findViewById(R.id.profile_picture);
        TextView name = findViewById(R.id.name);
        TextView birthday = findViewById(R.id.birthday);
        TextView country = findViewById(R.id.country);

        // Set @+id/profile_picture ImageView content
        profile_picture.setImageResource(R.drawable.avatar);
        //Set @+id/name TextView content
        name.setText(R.string.nameData);
        //Set @+id/birthday TextView content
        birthday.setText(R.string.birthdayData);
        //Set @+id/country TextView content
        country.setText(R.string.countryData);

        // Set text's Sizes
        name.setTextSize(24);
        birthday.setTextSize(24);
        country.setTextSize(24);

        // Set text's Styles
        name.setTypeface(null, Typeface.BOLD);
        birthday.setTypeface(null, Typeface.BOLD_ITALIC);
        country.setTypeface(null, Typeface.BOLD);

        // Set TextViews text's colors
        // Test options listed in http://android4beginners.com/2013/06/appendix-b-everything-about-colors-in-android/#more-204
        name.setTextColor(0xFF3F51B5);
        birthday.setTextColor(Color.parseColor("#3F51B5"));
        country.setTextColor(getResources().getColor(R.color.colorPrimary));
    }
}
