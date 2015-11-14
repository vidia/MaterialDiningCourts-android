package com.davidtschida.materialdiningcourts;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.orm.SugarApp;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by david on 11/3/2015.
 */
public class DiningCourtApplication extends SugarApp /*Required to init Sugar ORM*/{
    @Override
    public void onCreate() {
        super.onCreate();
        Iconify.with(new FontAwesomeModule()); //Required to init Iconify
        JodaTimeAndroid.init(this); //Required to init JodaTime Android
    }
}
