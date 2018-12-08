package com.example.asus.weatherforcast.setting;

import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.example.asus.weatherforcast.R;

public class SettingFragment extends PreferenceFragmentCompat {
    private Preference mLocationPreference;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.pref_settings,rootKey);
        mLocationPreference=findPreference(getString(R.string.KEY_SETTING_LOCATION));
        mLocationPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                //todo
                return true;
            }
        });


    }
}
