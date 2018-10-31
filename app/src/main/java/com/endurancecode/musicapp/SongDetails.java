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
        int songAlbumCover = getIntent().getIntExtra("ALBUM_COVER_ART", 0);
        int songYear = getIntent().getIntExtra("YEAR", 0);
        String songGenre = getIntent().getStringExtra("GENRE");
        String songLength = getIntent().getStringExtra("LENGTH");

        TextView songTitleView = (TextView) findViewById(R.id.song_title);
        songTitleView.setText(songTitle);

        TextView songArtistView = (TextView) findViewById(R.id.song_artist);
        songArtistView.setText(songArtist);

        TextView songAlbumView = (TextView) findViewById(R.id.song_album);
        songAlbumView.setText(songAlbum);

        ImageView songAlbumCoverArtView = (ImageView) findViewById(R.id.song_album_cover_art);
        songAlbumCoverArtView.setImageResource(songAlbumCover);

        TextView songYearView = (TextView) findViewById(R.id.song_year);
        songYearView.setText(String.valueOf(songYear));

        TextView songGenreView = (TextView) findViewById(R.id.song_genre);
        songGenreView.setText(songGenre);

        TextView songLengthView = (TextView) findViewById(R.id.songItemLength);
        songLengthView.setText(songLength);

        /* Find the "Songs" button */
        Button songsButton = (Button) findViewById(R.id.songs_button);
        /* Set OnClickListener method for Songs List View */
        songsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent songsListIntent = new Intent(SongDetails.this, SongsListActivity.class);
                startActivity(songsListIntent);
            }
        });

        /* Find the "Play" button */
        Button playButton = (Button) findViewById(R.id.play_button);
        /* Set OnClickListener method to play song
         * As actually playing the song isn't in the scope of this project, a toast will be shown
         */
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), getString(R.string.button_play_toast_message), Toast.LENGTH_LONG).show();
            }
        });
    }
}
