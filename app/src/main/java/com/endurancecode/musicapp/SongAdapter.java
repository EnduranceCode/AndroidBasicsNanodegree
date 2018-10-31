package com.endurancecode.musicapp;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * {@link SongAdapter} is an {@link ArrayAdapter} that can provide the layout for each list
 * based on a data source, which is a list of {@link Song} objects.
 */
public class SongAdapter extends ArrayAdapter<Song> {
    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context The current context. Used to inflate the layout file.
     * @param songs   A List of Song objects to display in a list
     */
    public SongAdapter(Activity context, ArrayList<Song> songs) {
        /* Here, we initialize the ArrayAdapter's internal storage for the context and the list.
         * the second argument is used when the ArrayAdapter is populating a single TextView.
         * Because this is a custom adapter for two TextViews and two ImageViews, the adapter is not
         * going to use this second argument, so it can be any value. Here, we used 0.
         */
        super(context, 0, songs);
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position    The position in the list of data that should be displayed in the
     *                    song item list view.
     * @param convertView The recycled view to populate.
     * @param parent      The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        /* Check if the existing view is being reused, otherwise inflate the view */
        View songItemListView = convertView;
        if (songItemListView == null) {
            songItemListView = LayoutInflater.from(getContext()).inflate(R.layout.song_item_list, parent, false);
        }

        /* Get the {@link Word} object located at this position in the list */
        final Song currentSong = getItem(position);

        /* Find the ImageView in the song_item_list.xml layout with the ID songItemCoverArt */
        ImageView coverArtImageView = (ImageView) songItemListView.findViewById(R.id.songItemCoverArt);
        // Get the album cover art from the current Song object and set it on the cover art ImageView
        coverArtImageView.setImageResource(currentSong.getSongCoverArt());

        /* Find the TextView in the song_item_list.xml layout with the ID songItemTitle */
        TextView songTitleTextView = (TextView) songItemListView.findViewById(R.id.songItemTitle);
        /* Get the song's title from the current Song object and set this text on the song title TextView */
        songTitleTextView.setText(currentSong.getSongTitle());

        /* Find the TextView in the song_item_list.xml layout with the ID */
        TextView songArtistTextView = (TextView) songItemListView.findViewById(R.id.songItemArtist);
        /* Get the song's artist from the current Song object and set this text on the song artist TextView */
        songArtistTextView.setText(currentSong.getSongArtist());

        /* Find the View in the song_item_list.xml that contains each Song item */
        View viewItem = (View) songItemListView.findViewById(R.id.song_item_list);
        /* Set an On Click Event Listener */
        viewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* Create Intent to open the Details Activity */
                Intent detailsIntent = new Intent(getContext(), SongDetails.class);

                /* Send song's details to Details Activity */
                detailsIntent.putExtra("TITLE", currentSong.getSongTitle());
                detailsIntent.putExtra("ARTIST", currentSong.getSongArtist());
                detailsIntent.putExtra("ALBUM", currentSong.getSongAlbum());
                detailsIntent.putExtra("ALBUM_COVER", currentSong.getSongCoverArt());
                detailsIntent.putExtra("YEAR", currentSong.getSongYear());
                detailsIntent.putExtra("GENRE", currentSong.getSongGenre());
                detailsIntent.putExtra("LENGTH", currentSong.getmSongLength());

                /* Start the detailsIntent (Song Details Activity) */
                getContext().startActivity(detailsIntent);
            }
        });

        /* Return the whole list item layout to be shown in the ListView */
        return songItemListView;
    }
}
