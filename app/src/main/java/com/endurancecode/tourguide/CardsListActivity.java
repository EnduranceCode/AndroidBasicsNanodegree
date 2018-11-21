package com.endurancecode.tourguide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class CardsListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Remove Action Bar drop shadow */
        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
        }

        /* Set the content of the activity to use the activity_main.xml layout file */
        setContentView(R.layout.activity_cards_list);

        /* Find the view pager that will allow the user to swipe between fragments */
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        /* Create an adapter that knows which fragment should be shown on each page */
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, getSupportFragmentManager());

        /* Set the adapter onto the view pager */
        viewPager.setAdapter(categoryAdapter);

        /* Find the tab layout that shows the tabs */
        TabLayout tabLayout = (TabLayout) findViewById(R.id.category_menu_tabs);

        /* Connect the tab layout with the view pager. This will
         *   1. Update the tab layout when the view pager is swiped
         *   2. Update the view pager when a tab is selected
         *   3. Set the tab layout's tab names with the view pager's adapter's titles
         *      by calling onPageTitle()
         */
        tabLayout.setupWithViewPager(viewPager);
    }
}
