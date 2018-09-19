package com.endurancecode.quizapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int score = 0;
    String userName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            score = savedInstanceState.getInt("SCORE");
            userName = savedInstanceState.getString("USER_NAME");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("SCORE", score);
        outState.putString("USER_NAME", userName);
    }

    public void calculateScore(View view) {
        // Checks Question 1
        EditText inputName = (EditText) findViewById(R.id.inputName);
        userName = inputName.getText().toString();
        // If the user does't enter his name the score isn't calculated
        // Code based in this answer: https://stackoverflow.com/a/6290587
        if (userName.equals("")) {
            Toast.makeText(this, getString(R.string.Q1ErrorMessage), Toast.LENGTH_LONG).show();
        }
        // When the user enters his name the score is calculated
        // Right answers taken from https://en.wikipedia.org/wiki/Triathlon_at_the_Summer_Olympics
        else {
            // Show score
            Toast.makeText(this, getString(R.string.showScore, userName, score), Toast.LENGTH_LONG).show();
        }
    }
}
