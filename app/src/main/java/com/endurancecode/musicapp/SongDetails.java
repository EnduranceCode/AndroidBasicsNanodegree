package com.endurancecode.musicapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

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
    }
}
