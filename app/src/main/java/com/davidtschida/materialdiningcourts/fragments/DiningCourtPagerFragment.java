package com.davidtschida.materialdiningcourts.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davidtschida.materialdiningcourts.R;
import com.davidtschida.materialdiningcourts.adapters.DiningCourtPagerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DiningCourtPagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiningCourtPagerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_MEAL = "arg_meal";

    // TODO: Rename and change types of parameters
    private String mMeal;

    @Bind(R.id.tabs) protected TabLayout tabLayout;
    @Bind(R.id.mealFragmentViewPager) protected ViewPager mViewPager;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private DiningCourtPagerAdapter mSectionsPagerAdapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param mealString Parameter 1.
     * @return A new instance of fragment DiningCourtPagerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DiningCourtPagerFragment newInstance(String mealString) {
        DiningCourtPagerFragment fragment = new DiningCourtPagerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MEAL, mealString);
        fragment.setArguments(args);
        return fragment;
    }

    public DiningCourtPagerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMeal = getArguments().getString(ARG_MEAL);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Timber.d("onAttach()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dining_court_pager, container, false);
        ButterKnife.bind(this, view);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new DiningCourtPagerAdapter(getChildFragmentManager(), mMeal);
        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
