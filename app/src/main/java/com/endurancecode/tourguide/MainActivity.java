package com.endurancecode.tourguide;

/* TODO: Set cards to open specific Fragment
 * https://stackoverflow.com/questions/36063704/how-to-launch-activity-and-show-specific-fragment
 * https://stackoverflow.com/questions/45664450/choosing-particular-fragment-to-display-in-activity
 * https://stackoverflow.com/questions/43178728/how-to-intent-into-specific-tab-fragment
 * https://stackoverflow.com/questions/33627106/how-to-open-a-specific-fragment-page-in-viewpager-from-another-activity-button-c
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
