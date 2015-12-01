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

import com.davidtschida.materialdiningcourts.R;
import com.davidtschida.materialdiningcourts.adapters.DiningCourtPagerAdapter;
import com.davidtschida.materialdiningcourts.eventbus.DateChosenEvent;
import com.davidtschida.materialdiningcourts.eventbus.EventBus;
import com.davidtschida.materialdiningcourts.eventbus.MealChosenEvent;
import com.davidtschida.materialdiningcourts.fragments.DatePickerFragment;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.squareup.otto.Produce;
import com.squareup.otto.Subscribe;

import org.joda.time.LocalDate;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MealViewActivity extends AppCompatActivity
{

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    @Bind(R.id.container)
    protected ViewPager mViewPager;
    @Bind(R.id.tabs)
    protected TabLayout tabLayout;
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
        if(mLastMealEvent != null) {
            setMealTitle(mLastMealEvent.getMeal(), mLastDateEvent.getLocalDate());
        }
    }

    @Subscribe @SuppressWarnings("unused")
    public void mealChosen(MealChosenEvent event) {
        Timber.i("Got the meal chosen event %s", event);
        mLastMealEvent = event;
        if(mLastDateEvent != null) {
            setMealTitle(mLastMealEvent.getMeal(), mLastDateEvent.getLocalDate());
        }
    }

    private void setMealTitle(String meal, LocalDate localDate) {
        if(getSupportActionBar() != null) {
            String dateString;
            if(localDate.equals(LocalDate.now())) {
                dateString = "Today";
            } else if(localDate.equals(LocalDate.now().plusDays(1))) {
                dateString = "Tomorrow";
            } else if(localDate.equals(LocalDate.now().plusDays(-1))) {
                dateString = "Yesterday";
            } else {
                dateString = localDate.toString("MM/dd/yyyy");
            }
            getSupportActionBar().setTitle(String.format("%s %s", meal, dateString));
        }
    }

    @Produce @SuppressWarnings("unused") public DateChosenEvent produceChosenDate() {
        if(mLastDateEvent == null) {
            mLastDateEvent = new DateChosenEvent(LocalDate.now());
        }
        return mLastDateEvent;
    }

    @Produce @SuppressWarnings("unused") public MealChosenEvent produceChosenMeal() {
        if(mLastMealEvent == null) {
            mLastMealEvent = new MealChosenEvent("Breakfast");
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
                            // Meal related options.
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

                            //Date Related options.
                            case R.id.nav_date_today:
                                EventBus.getBus().post(new DateChosenEvent(LocalDate.now()));
                                return true;
                            case R.id.nav_date_tomorrow:
                                EventBus.getBus().post(new DateChosenEvent(LocalDate.now().plusDays(1)));
                                return true;
                            case R.id.nav_pick_date:
                                DialogFragment newFragment = new DatePickerFragment();
                                newFragment.show(getSupportFragmentManager(), "datePicker");
                                return true;
                        }

                        return false;
                    }
                });
    }

    private void setupToolbar() {
        //Set up the spinner in the toolbar with meals data.
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Menus");
            getSupportActionBar().setDisplayShowTitleEnabled(true);
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
        }

        return super.onOptionsItemSelected(item);
    }
}
