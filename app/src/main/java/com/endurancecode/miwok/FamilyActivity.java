package com.endurancecode.miwok;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        /* Create an ArrayList that stores objects of type Word. Those objects consist of pair
         * of words: one word is the Miwoki word and the other it's its default translation
         * The Word objects are instantiated with the Word class.
         */
        ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word("father", "әpә"));
        words.add(new Word("mother", "әṭa"));
        words.add(new Word("son", "angsi"));
        words.add(new Word("daughter", "tune"));
        words.add(new Word("older brother", "taachi"));
        words.add(new Word("younger brother", "chalitti"));
        words.add(new Word("older sister", "teṭe"));
        words.add(new Word("younger sister", "kolliti"));
        words.add(new Word("grandmother", "ama"));
        words.add(new Word("grandfather", "paapa"));

        /* Create a custom {@link WordAdapter}, whose data source is a list of Word objects. The
         * adapter uses list_item.xml layout to create layouts for each item in the list.
         * This list item layout contains two {@link TextView}, which the adapter will set to
         * display the two words contained by each Word object in the ArrayList words.
         */
        WordAdapter wordAdapter = new WordAdapter(this, words);

        /* Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
         * There should be a {@link ListView} with the view ID called list, which is declared in the
         * word_listyout file.
         */
        ListView listView = (ListView) findViewById(R.id.list);

        /* Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
         * {@link ListView} will display list items for each word in the list of words.
         * Do this by calling the setAdapter method on the {@link ListView} object and pass in
         */
        listView.setAdapter(wordAdapter);
    }
}
