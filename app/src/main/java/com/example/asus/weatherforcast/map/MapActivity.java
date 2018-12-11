package com.example.asus.weatherforcast.map;

import android.support.v4.app.Fragment;

import com.example.asus.weatherforcast.SingleFragmentActivity;

public class MapActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new MapFragment();
    }
}
