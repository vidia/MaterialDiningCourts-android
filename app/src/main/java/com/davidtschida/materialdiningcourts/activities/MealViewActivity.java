package com.davidtschida.materialdiningcourts.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.davidtschida.materialdiningcourts.R;
import com.davidtschida.materialdiningcourts.adapters.DiningCourtPagerAdapter;
import com.davidtschida.materialdiningcourts.fragments.DatePickerFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MealViewActivity
        extends AppCompatActivity implements AdapterView.OnItemSelectedListener
        //implements DatePickerDialog.OnDateSetListener
{

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    @Bind(R.id.container)
    protected ViewPager mViewPager;
    @Bind(R.id.tabs)
    protected TabLayout tabLayout;
    @Bind(R.id.meal_chooser_spinner)
    protected Spinner mMealSpinner;
    @Bind(R.id.toolbar)
    protected Toolbar toolbar;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private DiningCourtPagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_view);
        ButterKnife.bind(this);

        setupToolbar();

        Timber.d("onCreate()");

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new DiningCourtPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(mViewPager);

        //mMealSpinner.setAdapter(new MealsSpinnerAdapter());

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.meals_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mMealSpinner.setAdapter(adapter);
        mMealSpinner.setOnItemSelectedListener(this);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_meal_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_pick_date) {
            DialogFragment newFragment = new DatePickerFragment();
            newFragment.show(getSupportFragmentManager(), "datePicker");

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Send event to refresh fragments. 
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
