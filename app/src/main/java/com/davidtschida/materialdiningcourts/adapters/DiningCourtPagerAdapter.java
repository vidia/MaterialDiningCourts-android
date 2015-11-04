package com.davidtschida.materialdiningcourts.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.davidtschida.materialdiningcourts.fragments.DayMenuFragment;
import com.davidtschida.purduemenu.models.Meal;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class DiningCourtPagerAdapter extends FragmentPagerAdapter {

    private static final String TAG = "DiningCourtPagerAdapter";
    private ArrayList<String> mDiningCourts = new ArrayList<>();

    public DiningCourtPagerAdapter(FragmentManager fm) {
        super(fm);
        mDiningCourts.addAll(Arrays.asList("Wiley", "Windsor", "Ford", "Hillenbrand", "Earhart"));
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a DayMenuFragment (defined as a static inner class below).

        LocalDate today = LocalDate.now();
        String formattedDate = today.toString("MM-dd-yyyy");
        Log.d(TAG, "Using the date: " + formattedDate + " for the menus");
        return DayMenuFragment.newInstance(mDiningCourts.get(position), Meal.LUNCH, formattedDate);
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