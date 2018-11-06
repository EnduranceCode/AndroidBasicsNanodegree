package com.endurancecode.SampleMusicPlayer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/* Music attribution:
 * Cha Cappella by Jimmy Fontanez/Media Right Productions
 * Downloaded from https://www.youtube.com/audiolibrary/music
 */

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Create MediaPlayer object with cha_cappella.mp3 */
        mediaPlayer = MediaPlayer.create(this, R.raw.cha_cappella);

        final int totalDuration = mediaPlayer.getDuration();

        /* Find buttons UI buttons */
        Button btnPlay = (Button) findViewById(R.id.button_play);
        Button btnPause = (Button) findViewById(R.id.button_pause);
        Button btnForward = (Button) findViewById(R.id.button_forward);

        /* Set OnClickListener method to the PLAY button */
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        Toast.makeText(MainActivity.this, "I'm done!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        /* Set OnClickListener method to the PAUSE button */
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
            }
        });

        /* Set OnClickListener method to the FORWARD button */
        btnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = mediaPlayer.getCurrentPosition();
                int forwardPosition = (int) (totalDuration - currentPosition) / 3;
                /* Moves the current playing position forward 1/3 of the remaining song duration */
                mediaPlayer.seekTo(currentPosition + forwardPosition);
            }
        });
    }
}
