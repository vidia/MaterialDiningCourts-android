package com.davidtschida.materialdiningcourts;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class DiningCourtPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<String> mDiningCourts = new ArrayList<>();

    public DiningCourtPagerAdapter(FragmentManager fm) {
        super(fm);
        mDiningCourts.addAll(Arrays.asList("Wiley", "Windsor", "Ford", "Hillenbrand", "Earhart"));
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return PlaceholderFragment.newInstance(mDiningCourts.get((position)));
    }

    @Override
    public int getCount() {
        // Show 5 total pages.
        return mDiningCourts.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mDiningCourts.get(position);
    }
}