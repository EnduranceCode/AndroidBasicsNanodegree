package com.endurancecode.PracticeSetObjectOrientedProgramming;

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
        TextView name = (TextView) findViewById(R.id.name);
        TextView birthday = (TextView) findViewById(R.id.birthday);
        TextView country = (TextView) findViewById(R.id.country);

        profile_picture.setImageResource(R.drawable.avatar);
        name.setText("Ricardo");
        birthday.setText("06-02-1971");
        country.setText("Portugal");
    }
}
