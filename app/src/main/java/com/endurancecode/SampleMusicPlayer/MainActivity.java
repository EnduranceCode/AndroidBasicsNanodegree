package com.endurancecode.SampleMusicPlayer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/* Music attribution:
 * Cha Cappella by Jimmy Fontanez/Media Right Productions
 * Downloaded from https://www.youtube.com/audiolibrary/music
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Create MediaPlayer object with cha_cappella.mp3 */
        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.cha_cappella);

        /* Find buttons UI buttons */
        Button btnPlay = (Button) findViewById(R.id.button_play);
        Button btnPause = (Button) findViewById(R.id.button_pause);

        /* Set OnClickListener method to the play button */
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
            }
        });

        /* Set OnClickListener method to the pause button */
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
            }
        });
    }
}
