package com.davidtschida.materialdiningcourts.activities;

import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.davidtschida.materialdiningcourts.R;
import com.davidtschida.materialdiningcourts.eventbus.DateChosenEvent;
import com.davidtschida.materialdiningcourts.eventbus.EventBus;
import com.davidtschida.materialdiningcourts.eventbus.MealChosenEvent;
import com.davidtschida.materialdiningcourts.eventbus.ShowSnackbarEvent;
import com.davidtschida.materialdiningcourts.fragments.DatePickerFragment;

import org.joda.time.LocalDate;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by david on 12/4/2015.
 */
public class NavDrawerActivity extends AppCompatActivity {

    @Bind(R.id.drawer_layout)
    protected DrawerLayout mDrawerLayout;
    @Bind(R.id.nav_view)
    protected NavigationView mNavigationView;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        setupDrawerContent();
    }

    private void setupDrawerContent() {
        mNavigationView.setNavigationItemSelectedListener(new NavDrawerActivity.NavDrawerItemSelectedListener());
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

    class NavDrawerItemSelectedListener implements NavigationView.OnNavigationItemSelectedListener {

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

                //Unimplemented selections
                case R.id.nav_favs:
                    EventBus.getBus().post(new ShowSnackbarEvent("Listing favorites is under construction!"));
                    return true;
            }

            return false;
        }
    }
}
