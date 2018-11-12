package com.endurancecode.miwok;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CategoryAdapter extends FragmentPagerAdapter {

    /* Context of the app */
    private Context mContextFragment;

    /**
     * Create a new {@link CategoryAdapter} object.
     *
     * @param context         is the context of the app
     * @param fragmentManager is the fragment manager that will keep each fragment's state in the adapter
     *                        across swipes.
     */
    public CategoryAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);

        mContextFragment = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new NumbersFragment();
            case 1:
                return new FamilyFragment();
            case 2:
                return new ColorsFragment();
            case 3:
                return new PhrasesFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContextFragment.getString(R.string.category_numbers);
            case 1:
                return mContextFragment.getString(R.string.category_family);
            case 2:
                return mContextFragment.getResources().getString(R.string.category_colors);
            case 3:
                return mContextFragment.getResources().getString(R.string.category_phrases);
            default:
                return null;
        }
    }
}
