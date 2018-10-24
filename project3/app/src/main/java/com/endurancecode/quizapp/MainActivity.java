package com.endurancecode.quizapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String userName = "";
    private String cityName = "";
    private int score = 0;
    private int numCorrectOptions = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            userName = savedInstanceState.getString("USER_NAME");
            userName = savedInstanceState.getString("CITY_NAME");
            score = savedInstanceState.getInt("SCORE");
            numCorrectOptions = savedInstanceState.getInt("NUM_CORRECT_OPTIONS");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("USER_NAME", userName);
        outState.putString("CITY_NAME", cityName);
        outState.putInt("SCORE", score);
        outState.putInt("NUM_CORRECT_OPTIONS", numCorrectOptions);
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
         * Q2:  9 Points
         * Q3:  9 Points
         * Q4: 12 Points
         * Q5: 12 Points
         * Q6:  7 Points for one correct answer
         *     14 Points for two correct answers
         *     21 Points for three correct answers
         *     28 Points for four (all) correct answers
         * Q7:  6 Points for one correct answer
         *     12 Points for two correct answers
         *     18 Points for three correct answers
         *     24 Points for three correct answers
         *     30 Points for five (all) correct answers
         *
         */
        else {
            // Reset score
            score = 0;

            // Check Question 2
            EditText inputCity = (EditText) findViewById(R.id.inputCity);
            cityName = inputCity.getText().toString();
            if (cityName.equals("Sidney") || cityName.equals("sidney")) {
                score += 9;
            }

            // Check Question 3
            if (((RadioButton) findViewById(R.id.Q3option2)).isChecked()) {
                score += 9;
            }

            // Check Question 4
            if (((RadioButton) findViewById(R.id.Q4option4)).isChecked()) {
                score += 12;
            }

            // Check Question 5
            if (((RadioButton) findViewById(R.id.Q5option5)).isChecked()) {
                score += 12;
            }

            // Check Question 6
            // Check if incorrect options are selected
            if (((CheckBox) findViewById(R.id.Q6Option2)).isChecked() ||
                    ((CheckBox) findViewById(R.id.Q6Option3)).isChecked() ||
                    ((CheckBox) findViewById(R.id.Q6Option5)).isChecked() ||
                    ((CheckBox) findViewById(R.id.Q6Option7)).isChecked() ||
                    ((CheckBox) findViewById(R.id.Q6Option8)).isChecked() ||
                    ((CheckBox) findViewById(R.id.Q6Option9)).isChecked()) {
                score += 0;
            } else {
                // Count number of correct options selected
                int numCorrectOptions = 0;
                // Check Option 1 of Question 6
                if (((CheckBox) findViewById(R.id.Q6Option1)).isChecked()) {
                    numCorrectOptions += 1;
                }
                // Check Option 4 of Question 6
                if (((CheckBox) findViewById(R.id.Q6Option4)).isChecked()) {
                    numCorrectOptions += 1;
                }
                // Check Option 6 of Question 6
                if (((CheckBox) findViewById(R.id.Q6Option6)).isChecked()) {
                    numCorrectOptions += 1;
                }
                // Check Option 10 of Question 6
                if (((CheckBox) findViewById(R.id.Q6Option10)).isChecked()) {
                    numCorrectOptions += 1;
                }
                // Update the score according to the number of corrected options selected
                score += numCorrectOptions * 7;
            }

            // Check Question 7
            // Check if incorrect options are selected
            if (((CheckBox) findViewById(R.id.Q7Option2)).isChecked() ||
                    ((CheckBox) findViewById(R.id.Q7Option6)).isChecked() ||
                    ((CheckBox) findViewById(R.id.Q7Option7)).isChecked() ||
                    ((CheckBox) findViewById(R.id.Q7Option8)).isChecked() ||
                    ((CheckBox) findViewById(R.id.Q7Option9)).isChecked() ||
                    ((CheckBox) findViewById(R.id.Q7Option11)).isChecked() ||
                    ((CheckBox) findViewById(R.id.Q7Option12)).isChecked()) {
                score += 0;
            } else {
                // Count number of correct options selected
                int numCorrectOptions = 0;
                // Check Option 1 of Question 7
                if (((CheckBox) findViewById(R.id.Q7Option1)).isChecked()) {
                    numCorrectOptions += 1;
                }
                // Check Option 3 of Question 7
                if (((CheckBox) findViewById(R.id.Q7Option3)).isChecked()) {
                    numCorrectOptions += 1;
                }
                // Check Option 4 of Question 7
                if (((CheckBox) findViewById(R.id.Q7Option4)).isChecked()) {
                    numCorrectOptions += 1;
                }
                // Check Option 4 of Question 7
                if (((CheckBox) findViewById(R.id.Q7Option5)).isChecked()) {
                    numCorrectOptions += 1;
                }
                // Check Option 10 of Question 7
                if (((CheckBox) findViewById(R.id.Q7Option10)).isChecked()) {
                    numCorrectOptions += 1;
                }
                score += numCorrectOptions * 6;
            }

            // Show score
            if (score >= 50) {
                Toast.makeText(this, getString(R.string.showScoreOver50, userName, score), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, getString(R.string.showScoreBelow50, userName, score), Toast.LENGTH_LONG).show();
            }
        }
    }
}
