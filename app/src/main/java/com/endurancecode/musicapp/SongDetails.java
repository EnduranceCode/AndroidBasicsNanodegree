package com.endurancecode.musicapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SongDetails extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Set the content of the activity to use the song_details.xmllayout file */
        setContentView(R.layout.song_details);

        /* Hide Action Bar */
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        /* Stores the data sent from SongAdapter */
        String songTitle = getIntent().getStringExtra("TITLE");
        String songArtist = getIntent().getStringExtra("ARTIST");
        String songAlbum = getIntent().getStringExtra("ALBUM");
        int songAlbumCover = getIntent().getIntExtra("ALBUM_COVER", 0);
        int songYear = getIntent().getIntExtra("YEAR", 0);
        String songGenre = getIntent().getStringExtra("GENRE");
        String songLength = getIntent().getStringExtra("LENGTH");

        TextView songTitleTextView = (TextView) findViewById(R.id.songItemTitle);
        songTitleTextView.setText(songTitle);

        TextView songArtistTextView = (TextView) findViewById(R.id.songItemArtist);
        songArtistTextView.setText(songArtist);

        TextView songAlbumTextView = (TextView) findViewById(R.id.songItemAlbum);
        songAlbumTextView.setText(songAlbum);

        ImageView songAlbumCoverArt = (ImageView) findViewById(R.id.songItemCoverArt);
        songAlbumCoverArt.setImageResource(songAlbumCover);

        TextView songYearTextView = (TextView) findViewById(R.id.songItemYear);
        songYearTextView.setText(String.valueOf(songYear));

        TextView songGenreTextView = (TextView) findViewById(R.id.songItemGenre);
        songGenreTextView.setText(songGenre);

        TextView songLengthTextView = (TextView) findViewById(R.id.songItemLength);
        songLengthTextView.setText(songLength);

        /* Find the "Songs" button */
        Button buttonSongs = (Button) findViewById(R.id.songsButton);
        /* Set OnClickListener method for Songs List View */
        buttonSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent songsListIntent = new Intent(SongDetails.this, SongsListActivity.class);
                startActivity(songsListIntent);
            }
        });

        /* Find the "Play" button */
        Button buttonPlay = (Button) findViewById(R.id.playButton);
        /* Set OnClickListener method to play song
         * As actually playing the song isn't in the scope of this project, a toast will be shown
         */
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), getString(R.string.button_play_toast_message), Toast.LENGTH_LONG).show();
            }
        });
    }
}
