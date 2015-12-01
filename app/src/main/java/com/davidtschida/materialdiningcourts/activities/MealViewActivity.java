package com.davidtschida.materialdiningcourts.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
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
import com.davidtschida.materialdiningcourts.eventbus.DateChosenEvent;
import com.davidtschida.materialdiningcourts.eventbus.EventBus;
import com.davidtschida.materialdiningcourts.eventbus.MealChosenEvent;
import com.davidtschida.materialdiningcourts.fragments.DatePickerFragment;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.squareup.otto.Produce;
import com.squareup.otto.Subscribe;

import org.joda.time.LocalDate;

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
    @Bind(R.id.drawer_layout)
    protected DrawerLayout mDrawerLayout;
    @Bind(R.id.nav_view)
    protected NavigationView mNavigationView;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private DiningCourtPagerAdapter mSectionsPagerAdapter;

    private DateChosenEvent mLastDateEvent;
    private MealChosenEvent mLastMealEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupToolbar();
        setupDrawerContent();
        //Must be done AFTER initing the spinner. Relies on spinner having a value.
        EventBus.getBus().register(this);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new DiningCourtPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Subscribe @SuppressWarnings("unused")
    public void dateChosen(DateChosenEvent event) {
        Timber.i("Got the date chosen event %s", event);
        mLastDateEvent = event;
    }

    @Subscribe @SuppressWarnings("unused")
    public void mealChosen(MealChosenEvent event) {
        Timber.i("Got the meal chosen event %s", event);
        mLastMealEvent = event;
    }

    @Produce @SuppressWarnings("unused") public DateChosenEvent produceChosenDate() {
        if(mLastDateEvent == null) {
            mLastDateEvent = new DateChosenEvent(LocalDate.now());
        }
        return mLastDateEvent;
    }

    @Produce @SuppressWarnings("unused") public MealChosenEvent produceChosenMeal() {
        if(mLastMealEvent == null) {
            mLastMealEvent = new MealChosenEvent((String)mMealSpinner.getSelectedItem());
        }
        return mLastMealEvent;
    }

    private void setupDrawerContent() {
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        switch(menuItem.getItemId()) {
                            case R.id.nav_pick_date:
                                DialogFragment newFragment = new DatePickerFragment();
                                newFragment.show(getSupportFragmentManager(), "datePicker");
                                return true;
                            case R.id.nav_breakfast:
                                EventBus.getBus().post(new MealChosenEvent("Breakfast"));
                                return true;
                            case R.id.nav_lunch:
                                EventBus.getBus().post(new MealChosenEvent("Lunch"));
                                return true;
                            case R.id.nav_late_lunch:
                                EventBus.getBus().post(new MealChosenEvent("Late Lunch"));
                                return true;
                            case R.id.nav_dinner:
                                EventBus.getBus().post(new MealChosenEvent("Dinner"));
                                return true;
                        }

                        return false;
                    }
                });
    }

    private void setupToolbar() {
        //Set up the spinner in the toolbar with meals data.

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.meals_array, R.layout.meals_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.meals_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mMealSpinner.setAdapter(adapter);
        mMealSpinner.setOnItemSelectedListener(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setHomeAsUpIndicator(new IconDrawable(this, MaterialIcons.md_menu)
                    .colorRes(R.color.white)
                    .actionBarSize());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getBus().unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_meal_view, menu);

        // Set an icon in the ActionBar
        menu.findItem(R.id.action_pick_date).setIcon(
                new IconDrawable(this, FontAwesomeIcons.fa_calendar)
                        .colorRes(R.color.white)
                        .actionBarSize());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_pick_date:
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Send event to refresh fragments.
        EventBus.getBus().post(new MealChosenEvent((String) parent.getItemAtPosition(position)));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
