package com.endurancecode.quizapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
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
        /* Question 1 is first checked, if the user doesn't enter his name the score isn't calculated
         *
         * Code used here is based based in this answer: https://stackoverflow.com/a/6290587
         */
        EditText inputName = (EditText) findViewById(R.id.inputName);
        userName = inputName.getText().toString();
        if (userName.equals("")) {
            Toast.makeText(this, getString(R.string.Q1ErrorMessage), Toast.LENGTH_LONG).show();
        }

        /* When the user enters his name the score is calculated
         * Right answers taken from https://en.wikipedia.org/wiki/Triathlon_at_the_Summer_Olympics
         *
         * Grades
         * Q2: 18 Points
         * Q3: 12 Points
         * Q4: 12 Points
         * Q5:  7 Points for each correct selected option (Total is 28 Points)
         * Q6:  6 Points for each correct selected option (Total is 30 Points)
         *
         */
        else {
            // Reset score
            score = 0;

            // Check Question 2
            if (((RadioButton) findViewById(R.id.Q2option2)).isChecked()) {
                score = score + 18;
            }

            // Check Question 3
            if (((RadioButton) findViewById(R.id.Q3option4)).isChecked()) {
                score = score + 12;
            }

            // Check Question 4
            if (((RadioButton) findViewById(R.id.Q4option5)).isChecked()) {
                score = score + 12;
            }

            // Check Question 5
            // Check Option 1 of Question 5
            if (((CheckBox) findViewById(R.id.Q5Option1)).isChecked()) {
                score = score + 7;
            }

            // Check Question 5
            // Check Option 4 of Question 5
            if (((CheckBox) findViewById(R.id.Q5Option4)).isChecked()) {
                score = score + 7;
            }

            // Check Question 5
            // Check Option 6 of Question 5
            if (((CheckBox) findViewById(R.id.Q5Option6)).isChecked()) {
                score = score + 7;
            }

            // Check Question 5
            // Check Option 10 of Question 5
            if (((CheckBox) findViewById(R.id.Q5Option10)).isChecked()) {
                score = score + 7;
            }

            // Check Question 6
            // Check Option 1 of Question 6
            if (((CheckBox) findViewById(R.id.Q6Option1)).isChecked()) {
                score = score + 6;
            }

            // Check Question 6
            // Check Option 3 of Question 6
            if (((CheckBox) findViewById(R.id.Q6Option3)).isChecked()) {
                score = score + 6;
            }

            // Check Question 6
            // Check Option 4 of Question 6
            if (((CheckBox) findViewById(R.id.Q6Option4)).isChecked()) {
                score = score + 6;
            }

            // Check Question 6
            // Check Option 4 of Question 5
            if (((CheckBox) findViewById(R.id.Q6Option5)).isChecked()) {
                score = score + 6;
            }

            // Check Question 6
            // Check Option 10 of Question 5
            if (((CheckBox) findViewById(R.id.Q6Option10)).isChecked()) {
                score = score + 6;
            }

            // Show score
            if (score >= 50) {
                Toast.makeText(this, getString(R.string.showScoreOver50, userName, score), Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, getString(R.string.showScoreBelow50, userName, score), Toast.LENGTH_LONG).show();
            }
        }
    }
}
