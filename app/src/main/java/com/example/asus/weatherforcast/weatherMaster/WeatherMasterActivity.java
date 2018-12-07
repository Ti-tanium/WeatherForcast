package com.example.asus.weatherforcast.weatherMaster;

import android.support.v4.app.Fragment;

import com.example.asus.weatherforcast.SingleFragmentActivity;

public class WeatherMasterActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new WeatherMasterFragment();
    }
}
