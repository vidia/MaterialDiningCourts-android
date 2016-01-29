package com.davidtschida.materialdiningcourts;

import android.app.Application;

import com.davidtschida.materialdiningcourts.datalogging.FoodItemTypeChangedManager;
import com.davidtschida.materialdiningcourts.eventbus.EventBus;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.MaterialModule;

import net.danlew.android.joda.JodaTimeAndroid;

import timber.log.Timber;

/**
 * Custom Application class for the app.
 * Initializes the necessary tools.
 */
public class DiningCourtApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Iconify.with(new FontAwesomeModule())
                .with(new MaterialModule());
        JodaTimeAndroid.init(this);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        if(BuildConfig.FLAVOR.equals("datalogger")) {
            EventBus.getBus().register(FoodItemTypeChangedManager.getInstance(this));
        }
    }
}
