package com.davidtschida.materialdiningcourts.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.AdapterView;

import com.davidtschida.materialdiningcourts.fragments.DayMenuFragment;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import timber.log.Timber;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class DiningCourtPagerAdapter extends FragmentPagerAdapter implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "DiningCourtPagerAdapter";
    private String mMealString;
    private ArrayList<String> mDiningCourts = new ArrayList<>();

    private List<DayMenuFragment> mFragments = new ArrayList<DayMenuFragment>();

    public DiningCourtPagerAdapter(FragmentManager fm) {
        super(fm);
        mDiningCourts.addAll(Arrays.asList("Wiley", "Windsor", "Ford", "Hillenbrand", "Earhart"));

        LocalDate today = LocalDate.now();

        for (String court : mDiningCourts) {
            mFragments.add(DayMenuFragment.newInstance(court, today));
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Timber.d("Meal was selected " + position);
        mMealString = (String) parent.getAdapter().getItem(position);
        for (DayMenuFragment frag : mFragments) {
            frag.setMeal(mMealString);
        }
        notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Timber.d("OnNothingSelected()");
    }
}