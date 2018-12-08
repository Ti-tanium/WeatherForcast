package com.example.asus.weatherforcast.setting;

import android.support.v4.app.Fragment;

import com.example.asus.weatherforcast.SingleFragmentActivity;

public class SettingActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new SettingFragment();
    }
}
