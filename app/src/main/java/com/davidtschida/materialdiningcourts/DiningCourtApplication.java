package com.davidtschida.materialdiningcourts;

import android.app.Application;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by david on 11/3/2015.
 */
public class DiningCourtApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Iconify.with(new FontAwesomeModule());
        JodaTimeAndroid.init(this);
    }
}
