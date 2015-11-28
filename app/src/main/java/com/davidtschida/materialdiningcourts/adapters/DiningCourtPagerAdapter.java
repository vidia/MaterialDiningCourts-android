package com.davidtschida.materialdiningcourts.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.davidtschida.materialdiningcourts.fragments.DayMenuFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class DiningCourtPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<String> mDiningCourts = new ArrayList<>();
    private List<DayMenuFragment> mFragments = new ArrayList<DayMenuFragment>();

    public DiningCourtPagerAdapter(FragmentManager fm) {
        super(fm);
        mDiningCourts.addAll(Arrays.asList("Earhart", "Ford", "Hillenbrand", "Wiley", "Windsor"));

        for (String court : mDiningCourts) {
            mFragments.add(DayMenuFragment.newInstance(court));
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mDiningCourts.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mDiningCourts.get(position);
    }
}