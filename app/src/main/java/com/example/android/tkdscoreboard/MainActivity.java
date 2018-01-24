package com.example.android.tkdscoreboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int redScore = 0;
    int blueScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Displays the given score for Red Fighter
     */
    public void displayRedScore(int score) {
        TextView scoreView = (TextView) findViewById(R.id.redFighterScore);
        scoreView.setText(String.valueOf(score));
    }

    /**
     * Displays the given score for Blue Fighter
     */
    public void displayBlueScore(int score) {
        TextView scoreView = (TextView) findViewById(R.id.blueFighterScore);
        scoreView.setText(String.valueOf(score));
    }

    /**
     * Update total score when Red Fighter Scores a Kick in the Head
     */
    public void redHeadPoint (View view) {
        redScore = redScore + 3;
        displayRedScore(redScore);
    }

    /**
     * Update total score when Red Fighter Scores a Turning Kick in the Body
     */
    public void redTurningPoint (View view) {
        redScore = redScore + 3;
        displayRedScore(redScore);
    }

    /**
     * Update total score when Red Fighter Scores a Kick in the Body
     */
    public void redBodyPoint (View view) {
        redScore = redScore + 2;
        displayRedScore(redScore);
    }

    /**
     * Update total score when Red Fighter gets a Penalty
     * When a fighter gets a Penalty, an extra point is awarded to his opponent
     */
    public void redFaultPoint (View view) {
        blueScore = blueScore + 1;
        displayBlueScore(blueScore);
    }

    /**
     * Update total score when Blue Fighter Scores a Kick in the Head
     */
    public void blueHeadPoint (View view) {
        blueScore = blueScore + 3;
        displayBlueScore(blueScore);
    }

    /**
     * Update total score when Blue Fighter Scores a Turning Kick in the Body
     */
    public void blueTurningPoint (View view) {
        blueScore = blueScore + 3;
        displayBlueScore(blueScore);
    }

    /**
     * Update total score when Blue Fighter Scores a Kick in the Body
     */
    public void blueBodyPoint (View view) {
        blueScore = blueScore + 2;
        displayRedScore(blueScore);
    }

    /**
     * Update total score when Blue Fighter gets a Penalty
     * When a fighter gets a Penalty, an extra point is awarded to his opponent
     */
    public void blueFaultPoint (View view) {
        redScore = redScore + 1;
        displayRedScore(redScore);
    }

    /**
     * Reset counters to zero
     */
    public void reset (View view) {
        redScore = 0;
        blueScore = 0;
        displayRedScore(redScore);
        displayBlueScore(blueScore);
    }
}
