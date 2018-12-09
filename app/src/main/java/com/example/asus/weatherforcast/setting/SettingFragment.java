package com.example.asus.weatherforcast.setting;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;

import com.example.asus.weatherforcast.Notification.PollService;
import com.example.asus.weatherforcast.R;


public class SettingFragment extends PreferenceFragmentCompat {
    private Preference mLocationPreference;
    private Preference mUnitPreference;
    private Preference mNotificationPreference;

    private SharedPreferences mSharedPreferences;
    private Activity mActivity;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.pref_settings,rootKey);
        mSharedPreferences=PreferenceManager.getDefaultSharedPreferences(getActivity());
        mActivity=getActivity();

        // initiate
        mLocationPreference=findPreference(getString(R.string.KEY_SETTING_LOCATION));
        mUnitPreference=findPreference(getString(R.string.KEY_SETTING_TEMPERATUREUNITS));
        mNotificationPreference=findPreference(getString(R.string.KEY_SETTING_WEATHERNOTIFICATION));

        // set summary
        boolean isBritishSystem=mSharedPreferences.getBoolean(getString(R.string.KEY_SETTING_TEMPERATUREUNITS),false);
        mUnitPreference.setSummary(isBritishSystem?"British":"Metric");
        mLocationPreference.setSummary(mSharedPreferences.getString(getString(R.string.KEY_SETTING_LOCATION),"长沙"));
        mNotificationPreference.setSummary(mSharedPreferences.getBoolean(getString(R.string.KEY_SETTING_WEATHERNOTIFICATION),false)?getString(R.string.enabled):getString(R.string.disabled));

        mLocationPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Log.i("newValue:",newValue.toString());
                preference.setSummary(newValue.toString());
                return true;
            }
        });

        mUnitPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary((boolean)newValue?"British":"Metric");
                return true;
            }
        });

        mNotificationPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                boolean isNotificationOn=(boolean)newValue;
                preference.setSummary(isNotificationOn?getString(R.string.enabled):getString(R.string.disabled));
                return true;
            }
        });


    }
}
