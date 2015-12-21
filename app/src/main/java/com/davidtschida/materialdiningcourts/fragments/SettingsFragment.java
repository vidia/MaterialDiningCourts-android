package com.davidtschida.materialdiningcourts.fragments;


import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.davidtschida.materialdiningcourts.R;

/**
 * Created by david on 12/15/2015.
 */
public class SettingsFragment  extends PreferenceFragmentCompat {
    @Override
    public void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.settings);
    }


}