package com.endurancecode.musicapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class SongsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.songs_list);

        // Hide Action Bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        /* Create an ArrayList that stores objects of type Song.
         *
         * This ArrayList is created with 12 objects and the information used is collected in the
         * following Wikipedia Pages:
         *  https://en.wikipedia.org/wiki/War_(U2_album)#Track_listing
         *  https://en.wikipedia.org/wiki/Under_a_Blood_Red_Sky
         *  https://en.wikipedia.org/wiki/Come_On_Pilgrim
         *  https://en.wikipedia.org/wiki/Surfer_Rosa
         *  https://en.wikipedia.org/wiki/Doolittle_(album)
         *  https://en.wikipedia.org/wiki/Nevermind
         *  https://en.wikipedia.org/wiki/In_Utero_(album)
         *
         * The copyright information of images of the album's cover art is stated in the following url's:
         * u2_war.jpg				    https://en.wikipedia.org/wiki/File:U2_War_album_cover.jpg
         * u2_under_a_blood_red_sky.jpg https://en.wikipedia.org/wiki/File:U2uabrs.jpg
         * pixies_come_on_pilgrim.jpg   https://en.wikipedia.org/wiki/File:Come_on_pilgrim.jpg
         * pixies_surfer_rosa.jpg	    https://en.wikipedia.org/wiki/File:SurferRosa.jpg
         * pixies_doolittle.jpg		    https://en.wikipedia.org/wiki/File:Pixies-Doolittle.jpg
         * Nirvana_nevermind.jpg	    https://en.wikipedia.org/wiki/File:NirvanaNevermindalbumcover.jpg
         * Nirvana_in_utero.jpg		    https://en.wikipedia.org/wiki/File:In_Utero_(Nirvana)_album_cover.jpg
         */
        ArrayList<Song> songs = new ArrayList<>();

        songs.add(new Song("Sunday bloody sunday", "U2", "War", R.drawable.u2_war, 1983, "Rock", "4:38"));
        songs.add(new Song("New year's day", "U2", "War", R.drawable.u2_war, 1983, "Rock", "5:38"));
        songs.add(new Song("11 o'clock tick tock", "U2", "Under a blood red sky", R.drawable.u2_under_a_blood_red_sky, 1983, "Rock", "4:34"));
        songs.add(new Song("Party Girl", "U2", "Under a Blood Red Sky", R.drawable.u2_under_a_blood_red_sky, 1983, "Rock", "4:34"));
        songs.add(new Song("Vamos", "Pixies", "Come on Pilgrim", R.drawable.pixies_come_on_pilgrim, 1987, "Rock", "2:53"));
        songs.add(new Song("Where is my mind", "Pixies", "Surfer Rosa", R.drawable.pixies_surfer_rosa, 1988, "Rock", "3:53"));
        songs.add(new Song("Cactus", "Pixies", "Surfer Rosa", R.drawable.pixies_surfer_rosa, 1988, "Rock", "2:16"));
        songs.add(new Song("Here comes your man", "Pixies", "Doolittle", R.drawable.pixies_doolittle, 1989, "Rock", "3:21"));
        songs.add(new Song("Smells like teen spirit", "Nirvana", "Nevermind", R.drawable.nirvana_nevermind, 1991, "Rock", "5:01"));
        songs.add(new Song("Come as you are", "Nirvana", "Nevermind", R.drawable.nirvana_nevermind, 1991, "Rock", "3:39"));
        songs.add(new Song("In bloom", "Nirvana", "Nevermind", R.drawable.nirvana_nevermind, 1991, "Rock", "4:14"));
        songs.add(new Song("Heart shaped box", "Nirvana", "In Utero", R.drawable.nirvana_in_utero, 1993, "Rock", "4:41"));

        /* Create a custom {@link SongAdapter}, whose data source is a list of Song objects. The
         * adapter uses song_item_list.xml layout to create layouts for each item in the list.
         * This list item layout contains two {@link TextView} and two {@link ImageView, which the
         * adapter will set to display some of the data of each Song object in the ArrayList songs.
         */
        SongAdapter songAdapter = new SongAdapter(this, songs);

        /* Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
         * There should be a {@link ListView} with the view ID called list, which is declared in the
         * word_list.xml file.
         */
        ListView listView = (ListView) findViewById(R.id.songs_list);

        /* Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
         * {@link ListView} will display list items for each word in the list of words.
         * Do this by calling the setAdapter method on the {@link ListView} object and pass in
         */
        listView.setAdapter(songAdapter);
    }
}
